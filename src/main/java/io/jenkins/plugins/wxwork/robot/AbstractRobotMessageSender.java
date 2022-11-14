package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.*;
import io.jenkins.plugins.wxwork.factory.HttpClientFactory;

/**
 * <p>AbstractRobotMessageSender</p>
 *
 * @author nekoimi 2022/07/16
 */
public abstract class AbstractRobotMessageSender implements RobotMessageSender {

    /**
     * <p>包装request</p>
     *
     * @param property
     * @param request
     * @return
     */
    abstract protected HttpRequest wrapRequest(RobotProperty property, RobotRequest request);

    /**
     * <p>包装response</p>
     *
     * @param httpResponse
     * @return
     */
    abstract protected RobotResponse wrapResponse(HttpResponse httpResponse);

    @Override
    public RobotResponse send(RobotProperty property, RobotRequest request) {
        return wrapResponse(HttpClientFactory.defaultInstance().send(wrapRequest(property, request)));
    }
}
