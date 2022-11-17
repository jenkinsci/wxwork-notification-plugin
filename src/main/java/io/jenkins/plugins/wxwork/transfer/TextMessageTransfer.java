package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.TextMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.utils.StrUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<String> textList = pipelineVars.getText();
        List<String> textCollect =
                textList.stream().filter(Objects::nonNull).filter(StrUtils::isNotBlank).collect(Collectors.toList());
        builder.content(String.join("\n", textCollect));
        return builder.build();
    }
}
