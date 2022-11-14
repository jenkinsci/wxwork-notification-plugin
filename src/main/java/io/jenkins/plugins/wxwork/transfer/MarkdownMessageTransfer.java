package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineBo;
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
    public RobotRequest transferRobotRequest(RobotPipelineBo pipelineBo) {
        MarkdownMessage.Builder builder = MarkdownMessage.builder();
        List<String> textList = pipelineBo.getText();
        List<String> textCollect = textList.stream().filter(StrUtils::isNotBlank).collect(Collectors.toList());
        builder.content(String.join("\n", textCollect));
        return builder.build();
    }
}
