package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotSender;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.message.MarkdownMessage;
import io.jenkins.plugins.wxwork.message.TextMessage;
import io.jenkins.plugins.wxwork.model.JobModel;
import io.jenkins.plugins.wxwork.model.RunUser;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotSender;
import io.jenkins.plugins.wxwork.utils.JenkinsUtils;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Pipeline支持</p>
 * <p>
 * 企业微信机器人文档：
 *
 * @author nekoimi 2022/07/12
 * @see <a href="https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win">https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win</a>
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class WXWorkPipeline extends Builder implements SimpleBuildStep {

    /**
     * 机器人ID
     */
    private String robot;

    /**
     * 消息类型
     */
    private MessageType type = MessageType.TEXT;

    /**
     * "@"成员列表，填写企业微信成员手机号
     */
    private Set<String> at = new HashSet<>();

    /**
     * 是否"@"所有人
     */
    private boolean atAll = false;

    /**
     * <p>构建环境</p>
     */
    private String buildEnv = "";

    /**
     * 消息内容
     */
    private List<String> text = new ArrayList<>();

    /**
     * <p>Jenkins地址</p>
     */
    private final String rootUrl = Jenkins.get().getRootUrl();

    /**
     * <p>机器人推送</p>
     */
    private final RobotSender robotSender = WXWorkRobotSender.instance();

    @DataBoundConstructor
    public WXWorkPipeline(String robot) {
        this.robot = robot;
    }

    @DataBoundSetter
    public void setType(String type) {
        if (type == null) {
            type = "text";
        }
        this.type = MessageType.typeValueOf(type);
    }

    @DataBoundSetter
    public void setAt(List<String> at) {
        if (at != null && !at.isEmpty()) {
            this.at = new HashSet<>(at);
        }
    }

    @DataBoundSetter
    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    @DataBoundSetter
    public void setBuildEnv(String buildEnv) {
        this.buildEnv = buildEnv;
    }

    @DataBoundSetter
    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener listener) throws InterruptedException, IOException {
        RunUser runUser = JenkinsUtils.getRunUser(run, listener);
        JobModel jobModel = JenkinsUtils.getJobModel(buildEnv, run, env, listener);
        RobotProperty property = WXWorkGlobalConfig.instance().getRobotPropertyById(robot);
        if (property == null) {
            listener.error("机器人[%s]配置找不到!", robot);
            return;
        }
        RobotRequest robotRequest;
        StringBuilder contentBuilder = new StringBuilder();
        if (type == MessageType.MARKDOWN) {
            contentBuilder.append(jobModel.asMarkdown()).append("\n");
            for (String s : text) {
                contentBuilder.append(s).append("\n");
            }
            robotRequest = MarkdownMessage.builder()
                    .content(contentBuilder.toString())
                    .build();
        } else if (type == MessageType.TEXT) {
            for (String s : text) {
                contentBuilder.append(s).append("\n");
            }
            robotRequest = TextMessage.builder()
                    .at(at)
                    .addAt(runUser.getMobile())
                    .atAll(atAll)
                    .content(contentBuilder.toString())
                    .build();
        } else {
            listener.error("消息类型[%s]不支持", type);
            return;
        }

        RobotResponse robotResponse = robotSender.send(property, robotRequest);
        if (robotResponse != null && robotResponse.isOk()) {
            // OK
            // System.out.println("OK");
        } else {
            listener.error(robotResponse.errorMessage());
        }
    }

    @Symbol("wxwork")
    @Extension
    public final static class WXWorkDescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return false;
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.displayName();
        }
    }
}
