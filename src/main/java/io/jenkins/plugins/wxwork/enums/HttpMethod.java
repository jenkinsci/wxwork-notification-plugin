package io.jenkins.plugins.wxwork.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>HttpMethod</p>
 *
 * @author nekoimi 2022/07/16
 */
public enum HttpMethod {

    /**
     * <p>GET</p>
     */
    GET("GET"),

    /**
     * <p>POST</p>
     */
    POST("POST");

    @JsonValue
    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
