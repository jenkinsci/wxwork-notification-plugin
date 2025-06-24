package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.MarkdownV2Message;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * @author nekoimi 2025/6/24 14:18
 * <p>
 * 企业微信机器人markdown_v2消息，对markdown支持更全面
 * https://developer.work.weixin.qq.com/document/path/91770#markdown-v2%E7%B1%BB%E5%9E%8B
 */
public class MarkdownV2MessageTransfer implements RobotMessageTransfer {

    @Override
    public boolean supports(MessageType messageType) {
        return MessageType.MARKDOWN_V2.equals(messageType);
    }

    @Override
    public RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars) {
        MarkdownV2Message.Builder builder = MarkdownV2Message.builder();
        builder.content(transferTextList(pipelineVars));
        return builder.build();
    }
}
