package io.jenkins.plugins.wxwork.service;

import io.jenkins.plugins.wxwork.sdk.Message;

/**
 * <p>RobotSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface RobotSender {

    /**
     * <p>发送消息</p>
     *
     * @param message
     */
    void send(Message message);
}
