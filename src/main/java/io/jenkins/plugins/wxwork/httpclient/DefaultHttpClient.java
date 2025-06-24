package io.jenkins.plugins.wxwork.httpclient;

import io.jenkins.plugins.wxwork.contract.HttpClient;
import io.jenkins.plugins.wxwork.contract.HttpRequest;
import io.jenkins.plugins.wxwork.contract.HttpResponse;
import io.jenkins.plugins.wxwork.protocol.DefaultHttpResponse;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

/**
 * <p>DefaultHttpClient</p>
 *
 * @author nekoimi 2022/07/16
 */
public class DefaultHttpClient implements HttpClient {
    private static final String IGNORE_SSL_DOMAIN = "qyapi.weixin.qq.com";
    private static final HostnameVerifier webhookApiHostnameVerifier = new WXWorkRobotWebHookApiHostnameVerifier();

    @Override
    public HttpResponse send(HttpRequest request) {
        DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(request.url());
            connection = getConnection(url, request.method().getValue(), request.contentType());
            try (OutputStream output = connection.getOutputStream()) {
                output.write(request.body());
                byte[] responseBytes = getResponseBytes(connection);
                defaultHttpResponse.setStatusCode(connection.getResponseCode());
                defaultHttpResponse.setBody(responseBytes);
            }
        } catch (Exception e) {
            defaultHttpResponse.setStatusCode(500);
            defaultHttpResponse.setErrorMessage(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return defaultHttpResponse;
    }

    private byte[] getResponseBytes(HttpURLConnection connection) throws IOException {
        int code = connection.getResponseCode();
        if (code < 200 || code > 300) {
            throw new IOException(code + " " + connection.getResponseMessage());
        }
        // default to success
        String encoding = connection.getContentEncoding();
        if ("gzip".equalsIgnoreCase(encoding)) {
            return getStreamAsString(new GZIPInputStream(connection.getInputStream()));
        } else {
            return getStreamAsString(connection.getInputStream());
        }
    }

    private byte[] getStreamAsString(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            StringBuilder response = new StringBuilder();
            final char[] buff = new char[1024];
            int read;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }
            return response.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    private HttpURLConnection getConnection(URL url, String method, String contentType) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection instanceof HttpsURLConnection httpsConnection) {
          try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
                httpsConnection.setSSLSocketFactory(ctx.getSocketFactory());
                httpsConnection.setHostnameVerifier(webhookApiHostnameVerifier);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        connection.setRequestMethod(method);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Host", url.getHost());
        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout(50000);
        connection.setReadTimeout(50000);
        return connection;
    }

    private static class TrustAllTrustManager implements X509TrustManager {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    private static class WXWorkRobotWebHookApiHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            if (IGNORE_SSL_DOMAIN.equals(hostname)) {
                return true;
            } else {
                HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
                return hostnameVerifier.verify(hostname, session);
            }
        }
    }
}
