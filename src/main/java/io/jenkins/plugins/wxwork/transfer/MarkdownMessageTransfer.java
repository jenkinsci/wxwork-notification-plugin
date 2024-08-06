package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.MarkdownMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>MarkdownMessageTransfer</p>
 *
 * @author nekoimi 2022/11/14
 */
public class MarkdownMessageTransfer implements RobotMessageTransfer {

    @Override
    public boolean supports(MessageType messageType) {
        return MessageType.MARKDOWN.equals(messageType);
    }

    @Override
    public RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars) {
        MarkdownMessage.Builder builder = MarkdownMessage.builder();
        builder.content(transferTextList(pipelineVars));
        return builder.build();
    }
}
