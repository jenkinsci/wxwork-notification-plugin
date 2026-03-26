package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import io.jenkins.plugins.wxwork.contract.RobotMessageSender;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotMessageSender;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import hudson.util.ListBoxModel;
import lombok.AccessLevel;
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
    @Getter(AccessLevel.NONE)
    private MessageType type = MessageType.TEXT;

    public String getType() {
        return type.getValue();
    }

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
        Set<String> atSet = FreeStyleJobHelper.parseAtSet(at);
        FreeStyleJobHelper.sendMessage(build, listener, robotSender, robot, type, content, atMe, atAll, atSet, imageUrl);
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
