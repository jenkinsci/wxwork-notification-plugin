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
     */
    HttpMethod method();

    /**
     * <p>请求地址</p>
     *
     */
    String url();

    /**
     * <p>请求体</p>
     *
     */
    byte[] body();

    /**
     * <p>Content-Type</p>
     *
     */
    default String contentType() {
        return "application/json";
    }
}
