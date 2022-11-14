package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.*;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotResponse;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotRequest;
import io.jenkins.plugins.wxwork.utils.JsonUtils;

/**
 * <p>WXWorkRobotMessageSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public class WXWorkRobotMessageSender extends AbstractRobotMessageSender {

    public static RobotMessageSender instance() {
        return SingletonHolder.robotMessageSender;
    }

    private static class SingletonHolder {
        private static final RobotMessageSender robotMessageSender = new WXWorkRobotMessageSender();
    }

    @Override
    protected HttpRequest wrapRequest(RobotProperty property, RobotRequest request) {
        return new WXWorkRobotRequest(property, request);
    }

    @Override
    protected RobotResponse wrapResponse(HttpResponse httpResponse) {
        if (httpResponse.statusCode() != 200) {
            return new WXWorkRobotResponse(httpResponse.statusCode(), httpResponse.errorMessage());
        }
        return JsonUtils.toBean(httpResponse.body(), WXWorkRobotResponse.class);
    }
}
