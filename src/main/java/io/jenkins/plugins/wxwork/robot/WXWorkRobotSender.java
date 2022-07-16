package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotSender;

/**
 * <p>WXWorkRobotSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public class WXWorkRobotSender implements RobotSender {

    public static RobotSender instance() {
        return SingletonHolder.robotSender;
    }

    @Override
    public RobotResponse send(RobotProperty property, RobotRequest request) {
        return null;
    }

    private static class SingletonHolder {
        private static final RobotSender robotSender = new WXWorkRobotSender();
    }
}
