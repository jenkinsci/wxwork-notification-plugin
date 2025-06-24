package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.Run;
import hudson.model.TaskListener;
import io.jenkins.plugins.wxwork.bo.RobotPipelineVars;
import io.jenkins.plugins.wxwork.bo.RunUser;
import io.jenkins.plugins.wxwork.contract.RobotMessageSender;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.factory.RobotMessageFactory;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotMessageSender;
import io.jenkins.plugins.wxwork.utils.JenkinsUtils;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.*;

/**
 * @author nekoimi 2024/8/6 23:56
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class WXWorkStep extends Step {
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
    public WXWorkStep(String robot) {
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

    /**
     * <p>发送机器人消息</p>
     *
     * @param run
     * @param workspace
     * @param envVars
     * @param listener
     */
    public void send(Run<?, ?> run, FilePath workspace, EnvVars envVars, TaskListener listener) {
        RobotProperty property = WXWorkGlobalConfig.instance().getRobotPropertyById(robot);
        if (Objects.isNull(property)) {
            listener.error("机器人[%s]配置找不到!", robot);
            return;
        }
        RunUser runUser = JenkinsUtils.getRunUser(run, listener);
        RobotPipelineVars pipelineVars = RobotPipelineVars.builder()
                .run(run).envVars(envVars).workspace(workspace).listener(listener)
                .runUser(runUser)
                .robot(JenkinsUtils.expandAll(run, workspace, listener, this.robot))
                .type(this.type).atMe(this.atMe).atAll(this.atAll).at(this.at)
                .text(this.text)
                .imageUrl(JenkinsUtils.expandAll(run, workspace, listener, this.imageUrl))
                .build();
        RobotRequest robotRequest = RobotMessageFactory.makeRobotRequest(pipelineVars);
        if (robotRequest == null) {
            listener.error("不支持的消息!");
            return;
        }
        RobotResponse robotResponse = robotSender.send(property, robotRequest);
        if (Objects.nonNull(robotResponse)) {
            if (robotResponse.isOk()) {
                listener.getLogger().println("WXWORK: 微信机器人[" + property.name() + "]推送消息成功!");
            } else {
                listener.error("WXWORK: 微信机器人[" + property.name() + "]推送消息失败：" + robotResponse.errorMessage());
            }
        }
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new WXWorkStepExecution(this, context);
    }

    @Override
    public StepDescriptor getDescriptor() {
        return new WXWorkStepDescriptor();
    }

    /**
     * 执行
     */
    static class WXWorkStepExecution extends StepExecution {
        @Serial
        private static final long serialVersionUID = 1L;
        private final transient WXWorkStep step;

        public WXWorkStepExecution(WXWorkStep step, @NonNull StepContext context) {
            super(context);
            this.step = step;
        }

        @Override
        public boolean start() throws Exception {
            StepContext context = this.getContext();

            Run<?, ?> run = context.get(Run.class);
            FilePath workspace = context.get(FilePath.class);
            EnvVars envVars = context.get(EnvVars.class);
            TaskListener listener = context.get(TaskListener.class);

            try {
                this.step.send(run, workspace, envVars, listener);
                context.onSuccess(true);
            } catch (Exception e) {
                context.onFailure(e);
            }

            return true;
        }
    }

    @Extension
    public static class WXWorkStepDescriptor extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return new HashSet<>() {{
              add(Run.class);
              add(TaskListener.class);
            }};
        }

        @Override
        public String getFunctionName() {
            return "wxwork";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.displayName();
        }
    }
}
