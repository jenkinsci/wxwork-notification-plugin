package io.jenkins.plugins.wxwork.contract;


/**
 * <p>RequestCall</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpClient {

    /**
     * <p>Call remote</p>
     *
     * @param request
     * @return
     */
    HttpResponse send(HttpRequest request);
}
