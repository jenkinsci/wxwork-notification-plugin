package io.jenkins.plugins.wxwork.contract;

import io.jenkins.plugins.wxwork.enums.HttpMethod;

/**
 * <p>HttpRequest</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface HttpRequest {

    /**
     * <p>请求方法</p>
     *
     * @return
     */
    HttpMethod method();

    /**
     * <p>请求地址</p>
     *
     * @return
     */
    String url();

    /**
     * <p>请求体</p>
     *
     * @return
     */
    byte[] body();

    /**
     * <p>Content-Type</p>
     *
     * @return
     */
    default String contentType() {
        return "application/json";
    }
}
