package io.jenkins.plugins.wxwork.contract;


/**
 * <p>HttpClient</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpClient {

    /**
     * <p>发送请求</p>
     *
     * @param request 请求
     */
    HttpResponse send(HttpRequest request);
}
