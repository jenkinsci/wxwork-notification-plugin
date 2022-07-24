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
import io.jenkins.plugins.wxwork.message.TextMessage;
import io.jenkins.plugins.wxwork.property.WXWorkRobotProperty;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotSender;
import io.jenkins.plugins.wxwork.utils.StrUtils;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>WXWorkBuilder</p>
 * <p>
 * 企业微信机器人文档：
 *
 * @author nekoimi 2022/07/12
 * @see <a href="https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win">https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win</a>
 *
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class WXWorkBuilder extends Builder implements SimpleBuildStep {

    /**
     * 机器人ID
     */
    private String robot;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * "@"成员列表，填写企业微信成员手机号
     */
    private Set<String> at;

    /**
     * 是否"@"所有人
     */
    private boolean atAll;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private List<String> text;

    private final String rootUrl = Jenkins.get().getRootUrl();

    private final RobotSender robotSender = WXWorkRobotSender.instance();

    @DataBoundConstructor
    public WXWorkBuilder(String robot) {
        this.robot = robot;
    }

    @DataBoundSetter
    public void setType(MessageType type) {
        if (type == null) {
            type = MessageType.TEXT;
        }
        this.type = type;
    }

    @DataBoundSetter
    public void setAt(Set<String> at) {
        if (at != null && !at.isEmpty()) {
            this.at = at;
        }
    }

    @DataBoundSetter
    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    @DataBoundSetter
    public void setTitle(String title) {
        this.title = title;
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
        List<WXWorkRobotProperty> robotPropertyList = WXWorkGlobalConfig.instance().getRobotPropertyList();
        RobotProperty property = null;
        RobotRequest robotRequest = null;
        for (WXWorkRobotProperty robotProperty : robotPropertyList) {
            if (Objects.equals(robot, robotProperty.getId())) {
                property = new WXWorkRobotProperty(robotProperty.getId(), robotProperty.getName(),
                        robotProperty.getWebhook());
            }
        }
        if (type == MessageType.TEXT) {
            TextMessage.Builder builder = TextMessage.builder();
            if (!at.isEmpty()) {
                builder = builder.at(at);
            }
            if (atAll) {
                builder = builder.atAll();
            }
            StringBuilder contentBuilder = new StringBuilder();
            if (StrUtils.isNotBlank(title)) {
                contentBuilder.append(title);
                contentBuilder.append("\n");
            }
            if (!text.isEmpty()) {
                text.forEach(t -> contentBuilder.append(t).append("\n"));
            }
            robotRequest = builder.content(contentBuilder.toString()).build();
        }
        if (property == null) {
            listener.error("机器人配置找不到!");
            return;
        }

        if (robotRequest == null) {
            listener.error("消息类型不受支持!");
            return;
        }

        RobotResponse robotResponse = WXWorkRobotSender.instance().send(property, robotRequest);
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
