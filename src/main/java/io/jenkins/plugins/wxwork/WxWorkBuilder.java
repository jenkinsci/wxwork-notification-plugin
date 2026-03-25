package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import hudson.util.ListBoxModel;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * <p>WxWorkBuilder</p>
 * <p>Build Step for FreeStyle project to send WeChat Work notification during build</p>
 *
 * @author nekoimi 2025/12/XX
 */
@Getter
@SuppressWarnings("unused")
public class WxWorkBuilder extends Builder {

    /**
     * Robot ID from global config
     */
    private final String robot;

    /**
     * Message type
     */
    private MessageType type = MessageType.TEXT;

    /**
     * Message content (multiline)
     */
    private String content;

    /**
     * @ trigger executor (build user)
     */
    private boolean atMe;

    /**
     * @ trigger all members
     */
    private boolean atAll;

    /**
     * @ trigger users by mobile (comma separated)
     */
    private String at;

    /**
     * Image URL for image type
     */
    private String imageUrl;

    private final transient RobotMessageSender robotSender = WXWorkRobotMessageSender.instance();

    @DataBoundConstructor
    public WxWorkBuilder(String robot) {
        this.robot = robot;
    }

    @DataBoundSetter
    public void setType(String type) {
        this.type = MessageType.typeValueOf(type);
    }

    @DataBoundSetter
    public void setContent(String content) {
        this.content = content;
    }

    @DataBoundSetter
    public void setAtMe(boolean atMe) {
        this.atMe = atMe;
    }

    @DataBoundSetter
    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    @DataBoundSetter
    public void setAt(String at) {
        this.at = at;
    }

    @DataBoundSetter
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        // Get robot config
        RobotProperty property = WXWorkGlobalConfig.instance().getRobotPropertyById(robot);
        if (Objects.isNull(property)) {
            listener.error("WXWORK: Robot [%s] configuration not found!", robot);
            return true;
        }

        // Get workspace
        FilePath workspace = build.getWorkspace();

        // Expand content
        String expandedContent = JenkinsUtils.expandAll(build, workspace, listener, content);
        List<String> textLines = new ArrayList<>();
        if (expandedContent != null && !expandedContent.isEmpty()) {
            textLines = Arrays.asList(expandedContent.split("\\R"));
        }

        // Process at list
        Set<String> atSet = new HashSet<>();
        if (at != null && !at.isEmpty()) {
            String[] mobiles = at.split(",");
            for (String mobile : mobiles) {
                String trimmed = mobile.trim();
                if (!trimmed.isEmpty()) {
                    atSet.add(trimmed);
                }
            }
        }

        // Get run user
        RunUser runUser = JenkinsUtils.getRunUser(build, listener);

        // Process atMe - add build user mobile to at list
        if (atMe && runUser.getMobile() != null && !runUser.getMobile().isEmpty()) {
            atSet.add(runUser.getMobile());
        }

        // Expand image URL
        String expandedImageUrl = null;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            expandedImageUrl = JenkinsUtils.expandAll(build, workspace, listener, imageUrl);
        }

        // Build pipeline vars and send
        var envVars = build.getEnvironment(listener);
        RobotPipelineVars pipelineVars = RobotPipelineVars.builder()
                .run(build).envVars(envVars).workspace(workspace).listener(listener)
                .runUser(runUser)
                .robot(robot)
                .type(this.type)
                .atMe(this.atMe)
                .atAll(this.atAll)
                .at(atSet)
                .text(textLines)
                .imageUrl(expandedImageUrl)
                .build();

        RobotRequest robotRequest = RobotMessageFactory.makeRobotRequest(pipelineVars);
        if (robotRequest == null) {
            listener.error("WXWORK: Unsupported message type");
            return true;
        }

        RobotResponse robotResponse = robotSender.send(property, robotRequest);
        if (Objects.nonNull(robotResponse)) {
            if (robotResponse.isOk()) {
                listener.getLogger().println("WXWORK: WeChat robot [" + property.name() + "] notification sent successfully!");
            } else {
                listener.error("WXWORK: WeChat robot [" + property.name() + "] notification failed: " + robotResponse.errorMessage());
            }
        }

        return true;
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.WxWorkBuilder_displayName();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        public ListBoxModel doFillRobotItems() {
            ListBoxModel listBoxModel = new ListBoxModel();
            WXWorkGlobalConfig.instance().getRobotPropertyList().forEach(robot -> {
                listBoxModel.add(robot.getName(), robot.getId());
            });
            return listBoxModel;
        }

        public ListBoxModel doFillTypeItems() {
            ListBoxModel listBoxModel = new ListBoxModel();
            Arrays.asList(MessageType.values()).forEach(messageType -> {
                listBoxModel.add(messageType.getTitle(), messageType.getValue());
            });
            return listBoxModel;
        }
    }
}
