package io.jenkins.plugins.wxwork.factory;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.contract.RobotMessageTransfer;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.transfer.ImageMessageTransfer;
import io.jenkins.plugins.wxwork.transfer.MarkdownMessageTransfer;
import io.jenkins.plugins.wxwork.transfer.TextMessageTransfer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>RobotMessageFactory</p>
 *
 * @author nekoimi 2022/11/14
 */
public class RobotMessageFactory {
    private static final List<RobotMessageTransfer> messageTransferProvider = new ArrayList<>();

    private RobotMessageFactory() {
    }

    public static RobotRequest makeRobotRequest(RobotPipelineVars pipelineBo) {
        for (RobotMessageTransfer messageTransfer : messageTransferProvider) {
            if (messageTransfer.supports(pipelineBo.getType())) {
                return messageTransfer.transferRobotRequest(pipelineBo);
            }
        }
        return null;
    }

    static {
        messageTransferProvider.add(new TextMessageTransfer());
        messageTransferProvider.add(new MarkdownMessageTransfer());
        messageTransferProvider.add(new ImageMessageTransfer());
    }
}
