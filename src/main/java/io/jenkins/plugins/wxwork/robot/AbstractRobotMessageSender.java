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
     * @param property 机器人配置
     * @param request 机器人请求
     */
    abstract protected HttpRequest wrapRequest(RobotProperty property, RobotRequest request);

    /**
     * <p>包装response</p>
     *
     * @param httpResponse http响应对象
     */
    abstract protected RobotResponse wrapResponse(HttpResponse httpResponse);

    @Override
    public RobotResponse send(RobotProperty property, RobotRequest request) {
        return wrapResponse(HttpClientFactory.defaultInstance().send(wrapRequest(property, request)));
    }
}
