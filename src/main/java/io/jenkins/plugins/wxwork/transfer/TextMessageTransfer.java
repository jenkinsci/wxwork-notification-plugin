package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineBo;
import io.jenkins.plugins.wxwork.bo.message.TextMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.utils.StrUtils;

import java.util.List;
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
    public RobotRequest transferRobotRequest(RobotPipelineBo pipelineBo) {
        TextMessage.Builder builder = TextMessage.builder();
        builder.at(pipelineBo.getAt());
        if (Boolean.TRUE.equals(pipelineBo.getAtMe())) {
            builder.addAt(pipelineBo.getRunUser().getMobile());
        }
        if (Boolean.TRUE.equals(pipelineBo.getAtAll())) {
            builder.atAll(true);
        }
        List<String> textList = pipelineBo.getText();
        List<String> textCollect = textList.stream().filter(StrUtils::isNotBlank).collect(Collectors.toList());
        builder.content(String.join("\n", textCollect));
        return builder.build();
    }
}
