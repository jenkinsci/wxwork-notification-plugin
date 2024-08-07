package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.TextMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>TextMessageTransfer</p>
 *
 * @author nekoimi 2022/11/14
 */
public class TextMessageTransfer implements RobotMessageTransfer {

    @Override
    public boolean supports(MessageType messageType) {
        return MessageType.TEXT.equals(messageType);
    }

    @Override
    public RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars) {
        TextMessage.Builder builder = TextMessage.builder();
        builder.at(pipelineVars.getAt());
        if (Boolean.TRUE.equals(pipelineVars.getAtMe())) {
            builder.addAt(pipelineVars.getRunUser().getMobile());
        }
        if (Boolean.TRUE.equals(pipelineVars.getAtAll())) {
            builder.atAll(true);
        }
        builder.content(transferTextList(pipelineVars));
        return builder.build();
    }
}
