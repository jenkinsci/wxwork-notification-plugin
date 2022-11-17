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
     * <p>使用默认的httpclient实现</p>
     *
     */
    public static HttpClient defaultInstance() {
        return new DefaultHttpClient();
    }
}
