package io.jenkins.plugins.wxwork.contract;

/**
 * <p>RobotMessageSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface RobotMessageSender {

    /**
     * <p>发送消息</p>
     *
     * @param request 请求实体
     */
    RobotResponse send(RobotProperty property, RobotRequest request);
}
