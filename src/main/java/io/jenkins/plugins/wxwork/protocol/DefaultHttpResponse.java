package io.jenkins.plugins.wxwork.protocol;

import io.jenkins.plugins.wxwork.contract.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>DefaultHttpResponse</p>
 *
 * @author nekoimi 2022/07/16
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultHttpResponse implements HttpResponse {

    /**
     * <p>http status code</p>
     */
    private Integer statusCode;
    /**
     * <p>http response body</p>
     */
    private byte[] body;
    /**
     * <p>异常消息</p>
     */
    private String errorMessage;

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public byte[] body() {
        return body;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
