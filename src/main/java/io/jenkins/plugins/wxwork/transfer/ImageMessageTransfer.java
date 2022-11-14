package io.jenkins.plugins.wxwork.transfer;

import io.jenkins.plugins.wxwork.bo.RobotPipelineBo;
import io.jenkins.plugins.wxwork.bo.message.ImageMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>ImageMessageTransfer</p>
 *
 * @author nekoimi 2022/11/14
 */
public class ImageMessageTransfer implements RobotMessageTransfer {

    @Override
    public boolean supports(MessageType messageType) {
        return MessageType.IMAGE.equals(messageType);
    }

    @Override
    public RobotRequest transferRobotRequest(RobotPipelineBo pipelineBo) {
        ImageMessage.Builder builder = ImageMessage.builder();
        builder.base64("");
        builder.md5("");
        return builder.build();
    }
}
