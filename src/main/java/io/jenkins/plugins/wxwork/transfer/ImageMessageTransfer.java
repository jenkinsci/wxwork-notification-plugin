package io.jenkins.plugins.wxwork.transfer;

import hudson.FilePath;
import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.message.ImageMessage;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.utils.DigestUtils;
import io.jenkins.plugins.wxwork.utils.StrUtils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

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
    public RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars) {
        ImageMessage.Builder builder = ImageMessage.builder();
        String imageUrl = pipelineVars.getImageUrl();
        if (StrUtils.isBlank(imageUrl)) {
            pipelineVars.getListener().error("图片地址为空!");
            return null;
        }
        FilePath imagePath = pipelineVars.getWorkspace().child(imageUrl);
        try {
            if (!imagePath.exists()) {
                pipelineVars.getListener().error("图片[" + imageUrl + "]文件不存在!");
            }
        } catch (Exception e) {
            pipelineVars.getListener().error(e.getMessage());
            return null;
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            pipelineVars.getListener().getLogger().println("图片地址: " + imagePath.toURI().toString());
            imagePath.copyTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            String base64encode = encoder.encodeToString(bytes);
            builder.base64(base64encode);
            builder.md5(DigestUtils.md5hex(bytes));
        } catch (Exception e) {
            pipelineVars.getListener().error(e.getMessage());
            return null;
        }
        return builder.build();
    }
}
