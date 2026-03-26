package io.jenkins.plugins.wxwork;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.RunUser;
import io.jenkins.plugins.wxwork.contract.RobotMessageSender;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.factory.RobotMessageFactory;
import io.jenkins.plugins.wxwork.utils.JenkinsUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>FreeStyleJobHelper</p>
 * <p>FreeStyle 项目（WxWorkBuilder / WxWorkNotifier）公共逻辑工具类</p>
 *
 * @author nekoimi
 */
public final class FreeStyleJobHelper {

    private FreeStyleJobHelper() {
    }

    /**
     * 解析逗号分隔的手机号字符串为 Set
     *
     * @param at 逗号分隔的手机号字符串
     * @return 手机号集合
     */
    public static Set<String> parseAtSet(String at) {
        Set<String> atSet = new HashSet<>();
        if (at != null && !at.isEmpty()) {
            for (String mobile : at.split(",")) {
                String trimmed = mobile.trim();
                if (!trimmed.isEmpty()) {
                    atSet.add(trimmed);
                }
            }
        }
        return atSet;
    }

    /**
     * 构建 RobotPipelineVars 并发送通知
     * <p>atMe 不在此方法中手动展开，由 Transfer 层统一处理</p>
     *
     * @param build       当前构建
     * @param listener    构建监听器
     * @param robotSender 消息发送器
     * @param robot       机器人 ID
     * @param type        消息类型
     * @param content     消息内容（原始，会自动展开环境变量）
     * @param atMe        是否 @ 构建执行人
     * @param atAll       是否 @ 所有人
     * @param atSet       指定 @ 的手机号集合
     * @param imageUrl    图片地址（仅图片类型有效，原始值，会自动展开环境变量）
     */
    public static void sendMessage(
            AbstractBuild<?, ?> build, BuildListener listener,
            RobotMessageSender robotSender,
            String robot, MessageType type, String content,
            boolean atMe, boolean atAll, Set<String> atSet, String imageUrl
    ) throws IOException, InterruptedException {
        RobotProperty property = WXWorkGlobalConfig.instance().getRobotPropertyById(robot);
        if (Objects.isNull(property)) {
            listener.error("WXWORK: Robot [%s] configuration not found!", robot);
            return;
        }

        FilePath workspace = build.getWorkspace();

        // 展开内容中的环境变量并按行分割
        String expandedContent = JenkinsUtils.expandAll(build, workspace, listener, content);
        List<String> textLines = new ArrayList<>();
        if (expandedContent != null && !expandedContent.isEmpty()) {
            textLines = Arrays.asList(expandedContent.split("\\R"));
        }

        // 展开图片地址中的环境变量
        String expandedImageUrl = null;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            expandedImageUrl = JenkinsUtils.expandAll(build, workspace, listener, imageUrl);
        }

        // 获取构建执行人信息
        RunUser runUser = JenkinsUtils.getRunUser(build, listener);

        // 构建 RobotPipelineVars 并发送
        var envVars = build.getEnvironment(listener);
        RobotPipelineVars pipelineVars = RobotPipelineVars.builder()
                .run(build).envVars(envVars).workspace(workspace).listener(listener)
                .runUser(runUser)
                .robot(robot)
                .type(type)
                .atMe(atMe)
                .atAll(atAll)
                .at(atSet)
                .text(textLines)
                .imageUrl(expandedImageUrl)
                .build();

        RobotRequest robotRequest = RobotMessageFactory.makeRobotRequest(pipelineVars);
        if (robotRequest == null) {
            listener.error("WXWORK: Unsupported message type");
            return;
        }

        RobotResponse robotResponse = robotSender.send(property, robotRequest);
        if (Objects.nonNull(robotResponse)) {
            if (robotResponse.isOk()) {
                listener.getLogger().println("WXWORK: WeChat robot [" + property.name() + "] notification sent successfully!");
            } else {
                listener.error("WXWORK: WeChat robot [" + property.name() + "] notification failed: " + robotResponse.errorMessage());
            }
        }
    }
}
