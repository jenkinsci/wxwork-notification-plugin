package io.jenkins.plugins.wxwork.factory;

import io.jenkins.plugins.wxwork.contract.HttpCaller;
import io.jenkins.plugins.wxwork.httpclient.DefaultHttpClient;

/**
 * <p>HttpCallFactory</p>
 *
 * @author nekoimi 2022/07/16
 */
public class HttpCallFactory {

    /**
     * <p>use default http caller</p>
     *
     * @return
     */
    public static HttpCaller make() {
        return new DefaultHttpClient();
    }
}
