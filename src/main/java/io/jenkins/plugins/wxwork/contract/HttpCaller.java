package io.jenkins.plugins.wxwork.contract;

/**
 * <p>RequestCall</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpCaller {

    /**
     * <p>Call remote</p>
     *
     * @param request
     * @return
     */
    HttpResponse call(HttpRequest request);
}
