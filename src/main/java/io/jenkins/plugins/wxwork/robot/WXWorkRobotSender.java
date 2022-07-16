package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.HttpResponse;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotSender;
import io.jenkins.plugins.wxwork.protocol.SendResponse;
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
    protected RobotResponse wrapResponse(HttpResponse httpResponse) {
        return JsonUtils.toBean(httpResponse.body(), SendResponse.class);
    }
}
