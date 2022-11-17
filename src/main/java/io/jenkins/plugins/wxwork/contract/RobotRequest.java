package io.jenkins.plugins.wxwork.contract;

import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>RobotRequest</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface RobotRequest {

    /**
     * <p>消息类型</p>
     *
     */
    MessageType messageType();

    /**
     * <p>toJson</p>
     *
     */
    String toJson();

    /**
     * <p>toBytes</p>
     */
    byte[] toBytes();
}
