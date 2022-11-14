package io.jenkins.plugins.wxwork.factory;

import io.jenkins.plugins.wxwork.contract.HttpClient;
import io.jenkins.plugins.wxwork.httpclient.DefaultHttpClient;

/**
 * <p>HttpCallFactory</p>
 *
 * @author nekoimi 2022/07/16
 */
public class HttpClientFactory {

    private HttpClientFactory() {
    }

    /**
     * <p>use default http client</p>
     *
     * @return
     */
    public static HttpClient make() {
        return new DefaultHttpClient();
    }
}
