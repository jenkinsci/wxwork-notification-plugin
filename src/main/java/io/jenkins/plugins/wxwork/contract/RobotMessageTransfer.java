package io.jenkins.plugins.wxwork.contract;

import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.utils.JenkinsUtils;
import io.jenkins.plugins.wxwork.utils.StrUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>RobotMessageTransfer</p>
 *
 * @author nekoimi 2022/11/14
 */
public interface RobotMessageTransfer {

    /**
     * <p>是否支持消息类型</p>
     *
     * @param messageType 消息类型 {@link MessageType}
     */
    boolean supports(MessageType messageType);

    /**
     * <p>生成机器人请求消息</p>
     *
     * @param pipelineVars Pipeline参数
     */
    RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars);

    /**
     * <p>处理文本列表消息</p>
     *
     * @param pipelineVars Pipeline参数
     * @return
     */
    default String transferTextList(RobotPipelineVars pipelineVars) {
        List<String> textCollect = pipelineVars.getText()
                .stream()
                .filter(Objects::nonNull)
                .filter(StrUtils::isNotBlank)
                .collect(Collectors.toList());
        return JenkinsUtils.expandAll(pipelineVars, String.join("\n", textCollect));
    }
}
