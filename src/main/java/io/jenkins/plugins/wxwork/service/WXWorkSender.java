package io.jenkins.plugins.wxwork.service;

import io.jenkins.plugins.wxwork.sdk.Message;

/**
 * <p>WXWorkSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public interface WXWorkSender {

    /**
     * <p>发送消息</p>
     *
     * @param message
     */
    void send(Message message);
}
