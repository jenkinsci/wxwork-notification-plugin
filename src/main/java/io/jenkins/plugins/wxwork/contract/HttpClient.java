package io.jenkins.plugins.wxwork.contract;


/**
 * <p>HttpClient</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpClient {

    /**
     * <p>send</p>
     *
     * @param request
     * @return
     */
    HttpResponse send(HttpRequest request);
}
