package io.jenkins.plugins.wxwork.contract;

/**
 * <p>HttpResponse</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpResponse {

    /**
     * <p>状态码</p>
     *
     * @return
     */
    int statusCode();

    /**
     * <p>响应体</p>
     *
     * @return
     */
    byte[] body();
}
