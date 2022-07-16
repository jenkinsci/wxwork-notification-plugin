package io.jenkins.plugins.wxwork.protocol;

import io.jenkins.plugins.wxwork.contract.HttpRequest;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>WXWorkRobotRequest</p>
 *
 * @author nekoimi 2022/07/16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WXWorkRobotRequest implements HttpRequest {

    /**
     * <p>RobotProperty</p>
     */
    protected RobotProperty property;

    /**
     * <p>RobotRequest</p>
     */
    protected RobotRequest request;

    /**
     * @param property
     * @param request
     * @return
     */
    public static HttpRequest of(RobotProperty property, RobotRequest request) {
        return new WXWorkRobotRequest(property, request);
    }

    @Override
    public HttpMethod method() {
        return HttpMethod.POST;
    }

    @Override
    public String url() {
        return property.webhook();
    }

    @Override
    public byte[] body() {
        return request.toBytes();
    }
}
