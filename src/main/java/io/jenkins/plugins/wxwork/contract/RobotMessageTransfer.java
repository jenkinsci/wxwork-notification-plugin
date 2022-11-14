package io.jenkins.plugins.wxwork.contract;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.enums.MessageType;

/**
 * <p>RobotMessageTransfer</p>
 *
 * @author nekoimi 2022/11/14
 */
public interface RobotMessageTransfer {

    /**
     * <p>是否支持消息类型</p>
     *
     * @param messageType
     * @return
     */
    boolean supports(MessageType messageType);

    /**
     * <p>生成机器人请求消息</p>
     *
     * @param pipelineVars
     * @return
     */
    RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars);
}
