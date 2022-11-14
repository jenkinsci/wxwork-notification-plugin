package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.*;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotResponse;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotRequest;
import io.jenkins.plugins.wxwork.utils.JsonUtils;

/**
 * <p>WXWorkRobotSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public class WXWorkRobotSender extends AbstractRobotSender {

    public static RobotSender instance() {
        return SingletonHolder.robotSender;
    }

    private static class SingletonHolder {
        private static final RobotSender robotSender = new WXWorkRobotSender();
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
