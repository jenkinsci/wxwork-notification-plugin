package io.jenkins.plugins.wxwork.utils;

import groovy.util.logging.Slf4j;
import hudson.EnvVars;
import hudson.model.*;
import io.jenkins.plugins.wxwork.WXWorkUserExtensionProperty;
import io.jenkins.plugins.wxwork.model.JobModel;
import io.jenkins.plugins.wxwork.model.RunUser;
import jenkins.model.Jenkins;

import java.util.stream.Collectors;

/**
 * <p>JenkinsUtils</p>
 *
 * @author nekoimi 2022/07/24
 */
@Slf4j
public class JenkinsUtils {

    /**
     * <p>获取构建信息</p>
     *
     * @param run
     * @param env
     * @param listener
     * @return
     */
    public static JobModel getJobModel(String buildEnv, Run<?, ?> run, EnvVars env, TaskListener listener) {
        Job<?, ?> job = run.getParent();
        // 项目信息
        String projectName = job.getFullDisplayName();
        String projectUrl = job.getAbsoluteUrl();
        // Job信息
        String jobName = run.getDisplayName();
        String jobUrl = Jenkins.get().getRootUrl() + run.getUrl();
        // 执行人信息
        RunUser runUser = getRunUser(run, listener);
        return JobModel.builder()
                .projectName(projectName)
                .projectUrl(projectUrl)
                .jobName(jobName)
                .jobUrl(jobUrl)
                .executorName(runUser.getName())
                .buildEnv(buildEnv)
                .build();
    }

    /**
     * <p>获取执行用户</p>
     *
     * @param run
     * @param listener
     * @return
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
            executorMobile = user.getProperty(WXWorkUserExtensionProperty.class).getMobile();
            if (executorMobile == null) {
                listener.error("用户【%s】暂未设置手机号码，请前往 %s 添加。", executorName, user.getAbsoluteUrl() + "/configure");
            }
        }
        return RunUser.builder()
                .name(executorName)
                .mobile(executorMobile)
                .build();
    }
}
