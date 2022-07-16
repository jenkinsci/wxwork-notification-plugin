package io.jenkins.plugins.wxwork;

import io.jenkins.plugins.wxwork.config.WXWorkRobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.message.TextMessage;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotSender;

/**
 * <p>SendTests</p>
 *
 * @author nekoimi 2022/07/16
 */
public class SendTests {

    public static void main(String[] args) {
        RobotProperty property = new WXWorkRobotProperty("id", "name", "");
        RobotRequest message = TextMessage.builder().content("企业微信机器人测试成功").atAll().build();
        RobotResponse robotResponse = WXWorkRobotSender.instance().send(property, message);
        if (robotResponse != null && robotResponse.isOk()) {
            // ok
            System.out.println("OK");
        } else {
            System.out.println(robotResponse.errorMessage());
        }
    }
}
