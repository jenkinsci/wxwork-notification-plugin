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
     */
    int statusCode();

    /**
     * <p>响应体</p>
     *
     */
    byte[] body();

    /**
     * <p>异常消息</p>
     *
     */
    String errorMessage();
}
