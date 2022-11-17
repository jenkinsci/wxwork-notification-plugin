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
import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.RunUser;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotMessageSender;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.factory.RobotMessageFactory;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotMessageSender;
import io.jenkins.plugins.wxwork.utils.JenkinsUtils;
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
 *
 * @author nekoimi 2022/07/12
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class WXWorkPipelineBuilder extends Builder implements SimpleBuildStep {

    /**
     * 机器人ID
     */
    private String robot;

    /**
     * 消息类型
     */
    private MessageType type = MessageType.TEXT;

    /**
     * 是否"@"我自己(当前构建执行人)
     */
    private Boolean atMe = false;

    /**
     * 是否"@"所有人
     */
    private Boolean atAll = false;

    /**
     * "@"成员列表，填写企业微信成员手机号
     */
    private Set<String> at = new HashSet<>();

    /**
     * Text/Markdown消息内容
     */
    private List<String> text = new ArrayList<>();

    /**
     * Image消息图片地址
     */
    private String imageUrl;

    /**
     * <p>机器人推送</p>
     */
    private final RobotMessageSender robotSender = WXWorkRobotMessageSender.instance();

    @DataBoundConstructor
    public WXWorkPipelineBuilder(String robot) {
        this.robot = robot;
    }

    @DataBoundSetter
    public void setType(String type) {
        this.type = MessageType.typeValueOf(type);
    }

    @DataBoundSetter
    public void setAt(List<String> at) {
        if (at != null && !at.isEmpty()) {
            this.at = new HashSet<>(at);
        }
    }

    @DataBoundSetter
    public void setAtMe(Boolean atMe) {
        this.atMe = atMe;
    }

    @DataBoundSetter
    public void setAtAll(Boolean atAll) {
        this.atAll = atAll;
    }

    @DataBoundSetter
    public void setText(List<String> text) {
        this.text = text;
    }

    @DataBoundSetter
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener listener) throws InterruptedException, IOException {
        RobotProperty property = WXWorkGlobalConfig.instance().getRobotPropertyById(robot);
        if (property == null) {
            listener.error("机器人[%s]配置找不到!", robot);
            return;
        }
        RunUser runUser = JenkinsUtils.getRunUser(run, listener);
        RobotPipelineVars pipelineVars = RobotPipelineVars.builder()
                .robot(this.robot).type(this.type).atMe(this.atMe).atAll(this.atAll)
                .at(this.at).text(this.text).imageUrl(this.imageUrl).runUser(runUser)
                .env(env).workspace(workspace).listener(listener).build();
        RobotRequest robotRequest = RobotMessageFactory.makeRobotRequest(pipelineVars);
        if (robotRequest == null) {
            listener.error("不支持的消息!");
            return;
        }
        RobotResponse robotResponse = robotSender.send(property, robotRequest);
        if (robotResponse != null && robotResponse.isOk()) {
            listener.getLogger().println("WXWORK: 微信机器人[" + property.name() + "]推送消息成功!");
        } else {
            listener.error(robotResponse.errorMessage());
        }
    }

    @Symbol({"wxwork", "wxWork"})
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
