package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.MarkdownMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.utils.StrUtils;

import java.util.List;
import java.util.stream.Collectors;

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
        List<String> textList = pipelineVars.getText();
        List<String> textCollect = textList.stream().filter(StrUtils::isNotBlank).collect(Collectors.toList());
        builder.content(pipelineVars.getEnvVars().expand(String.join("\n", textCollect)));
        return builder.build();
    }
}
