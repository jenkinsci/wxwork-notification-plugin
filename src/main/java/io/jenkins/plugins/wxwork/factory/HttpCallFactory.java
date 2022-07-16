package io.jenkins.plugins.wxwork.factory;

import io.jenkins.plugins.wxwork.contract.HttpCaller;
import io.jenkins.plugins.wxwork.contract.HttpRequest;
import io.jenkins.plugins.wxwork.contract.HttpResponse;

/**
 * <p>HttpCallFactory</p>
 *
 * @author nekoimi 2022/07/16
 */
public class HttpCallFactory {

    public static HttpCaller make() {
        return new HttpCaller() {
            @Override
            public HttpResponse call(HttpRequest request) {
                return null;
            }
        };
    }
}
