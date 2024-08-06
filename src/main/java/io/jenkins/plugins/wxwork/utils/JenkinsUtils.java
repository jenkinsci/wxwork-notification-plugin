package io.jenkins.plugins.wxwork.utils;

import groovy.util.logging.Slf4j;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.Cause;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.User;
import io.jenkins.plugins.wxwork.WXWorkUserExtensionProperty;
import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.RunUser;
import org.jenkinsci.plugins.tokenmacro.MacroEvaluationException;
import org.jenkinsci.plugins.tokenmacro.TokenMacro;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * <p>JenkinsUtils</p>
 *
 * @author nekoimi 2022/11/14
 */
@Slf4j
public class JenkinsUtils {

    private JenkinsUtils() {
    }

    /**
     * 处理内容包含的环境变量
     * @param pipelineVars
     * @param text
     * @return
     */
    public static String expandAll(RobotPipelineVars pipelineVars, String text) {
        try {
            return TokenMacro.expandAll(
                    pipelineVars.getRun(),
                    pipelineVars.getWorkspace(),
                    pipelineVars.getListener(),
                    text
            );
        } catch (MacroEvaluationException | IOException | InterruptedException e) {
            pipelineVars.getListener().getLogger().println("企业微信插件处理环境变量异常: " + e.getMessage());
            return pipelineVars.getEnvVars().expand(text);
        }
    }

    /**
     * 处理内容包含的环境变量
     *
     * @param run
     * @param workspace
     * @param listener
     * @param text
     * @return
     */
    public static String expandAll(Run<?, ?> run, FilePath workspace, TaskListener listener, String text) {
        try {
            return TokenMacro.expandAll(run, workspace, listener, text);
        } catch (MacroEvaluationException | IOException | InterruptedException e) {
            listener.getLogger().println("企业微信插件处理环境变量异常: " + e.getMessage());

            try {
                EnvVars envVars = run.getEnvironment(listener);
                return envVars.expand(text);
            } catch (IOException | InterruptedException ex) {
                listener.getLogger().println("企业微信插件处理环境变量异常: " + e.getMessage());
            }
            return "";
        }
    }

    /**
     * <p>获取执行用户</p>
     *
     * @param run      {@link Run}
     * @param listener {@link TaskListener}
     */
    public static RunUser getRunUser(Run<?, ?> run, TaskListener listener) {
        Cause.UserIdCause userIdCause = run.getCause(Cause.UserIdCause.class);
        // 执行人信息
        User user = null;
        String executorName;
        String executorMobile = null;
        if (userIdCause != null && userIdCause.getUserId() != null) {
            user = User.getById(userIdCause.getUserId(), false);
        }
        if (user == null) {
            listener.error("未获取到构建人信息，将尝试从构建信息中模糊匹配。");
            executorName = run.getCauses().stream().map(Cause::getShortDescription).collect(Collectors.joining());
        } else {
            executorName = user.getDisplayName();
            WXWorkUserExtensionProperty executorProperty = user.getProperty(WXWorkUserExtensionProperty.class);
            if (executorProperty == null) {
                listener.error("用户【%s】暂未设置手机号码，请前往 %s 添加。", executorName, user.getAbsoluteUrl() + "/configure");
            } else {
                executorMobile = executorProperty.getMobile();
                if (StrUtils.isBlank(executorMobile)) {
                    listener.error("用户【%s】暂未设置手机号码，请前往 %s 添加。", executorName, user.getAbsoluteUrl() + "/configure");
                }
            }
        }
        return RunUser.builder().name(executorName).mobile(executorMobile).build();
    }
}
