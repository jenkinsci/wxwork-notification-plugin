package io.jenkins.plugins.wxwork.sdk;

import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>RobotMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface Message {

    /**
     * <p>消息类型</p>
     *
     * @return
     */
    MessageType msgType();

    /**
     * <p>toJson</p>
     *
     * @return
     */
    String toJson();

    /**
     * <p>toBytes</p>
     * @return
     */
    byte[] toBytes();
}
