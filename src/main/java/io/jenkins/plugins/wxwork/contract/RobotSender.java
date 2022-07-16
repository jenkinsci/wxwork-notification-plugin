package io.jenkins.plugins.wxwork.contract;

/**
 * <p>RobotSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface RobotSender {

    /**
     * <p>发送消息</p>
     *
     * @param request
     */
    RobotResponse send(RobotProperty property, RobotRequest request);
}
