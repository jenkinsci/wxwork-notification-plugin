package io.jenkins.plugins.wxwork.protocol;

import io.jenkins.plugins.wxwork.contract.HttpRequest;
import io.jenkins.plugins.wxwork.enums.HttpMethod;

/**
 * <p>RobotFileHttpRequest</p>
 *
 * @author nekoimi 2022/07/16
 */
public class RobotFileHttpRequest implements HttpRequest {
    @Override
    public HttpMethod method() {
        return null;
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public byte[] body() {
        return new byte[0];
    }
}
