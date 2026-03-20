package io.jenkins.plugins.wxwork.httpclient;

import hudson.ProxyConfiguration;
import io.jenkins.plugins.wxwork.contract.HttpClient;
import io.jenkins.plugins.wxwork.contract.HttpRequest;
import io.jenkins.plugins.wxwork.contract.HttpResponse;
import io.jenkins.plugins.wxwork.protocol.DefaultHttpResponse;

import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * <p>DefaultHttpClient</p>
 *
 * <p>使用 Jenkins ProxyConfiguration 提供的 HttpClient，自动处理代理配置</p>
 *
 * @author nekoimi 2022/07/16
 */
public class DefaultHttpClient implements HttpClient {

    @Override
    public HttpResponse send(HttpRequest request) {
        DefaultHttpResponse response = new DefaultHttpResponse();
        try {
            // 使用 Jenkins 提供的 HttpClient（自动处理代理）
            java.net.http.HttpClient httpClient = ProxyConfiguration.newHttpClient();

            // 构建请求
            java.net.http.HttpRequest.Builder requestBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(URI.create(request.url()))
                    .header("Content-Type", request.contentType())
                    .method(request.method().getValue(), BodyPublishers.ofByteArray(request.body()));

            // 发送请求
            java.net.http.HttpResponse<byte[]> httpResponse = httpClient.send(
                    requestBuilder.build(),
                    BodyHandlers.ofByteArray());

            response.setStatusCode(httpResponse.statusCode());
            response.setBody(httpResponse.body());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}