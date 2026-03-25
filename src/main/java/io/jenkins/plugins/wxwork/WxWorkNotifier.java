package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.Run;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
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
 * <p>WxWorkNotifier</p>
 * <p>Post-build Action for FreeStyle project to send WeChat Work notification</p>
 *
 * @author nekoimi 2025/12/XX
 */
@Getter
@SuppressWarnings("unused")
public class WxWorkNotifier extends Notifier {

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
     * Notify on build success
     */
    private boolean notifySuccess;

    /**
     * Notify on build failure
     */
    private boolean notifyFailure;

    /**
     * Notify on build unstable
     */
    private boolean notifyUnstable;

    /**
     * Notify on recovery (success after failure)
     */
    private boolean notifyRecovery;

    /**
     * Success message content (multiline)
     */
    private String successContent;

    /**
     * Failure message content (multiline)
     */
    private String failureContent;

    /**
     * Unstable message content (multiline)
     */
    private String unstableContent;

    /**
     * Recovery message content (multiline)；为空时回退到 successContent 保持向下兼容
     */
    @Getter(AccessLevel.NONE)
    private String recoveryContent;

    public String getRecoveryContent() {
        return recoveryContent;
    }

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
    public WxWorkNotifier(String robot) {
        this.robot = robot;
    }

    @DataBoundSetter
    public void setType(String type) {
        this.type = MessageType.typeValueOf(type);
    }

    @DataBoundSetter
    public void setNotifySuccess(boolean notifySuccess) {
        this.notifySuccess = notifySuccess;
    }

    @DataBoundSetter
    public void setNotifyFailure(boolean notifyFailure) {
        this.notifyFailure = notifyFailure;
    }

    @DataBoundSetter
    public void setNotifyUnstable(boolean notifyUnstable) {
        this.notifyUnstable = notifyUnstable;
    }

    @DataBoundSetter
    public void setNotifyRecovery(boolean notifyRecovery) {
        this.notifyRecovery = notifyRecovery;
    }

    @DataBoundSetter
    public void setSuccessContent(String successContent) {
        this.successContent = successContent;
    }

    @DataBoundSetter
    public void setFailureContent(String failureContent) {
        this.failureContent = failureContent;
    }

    @DataBoundSetter
    public void setUnstableContent(String unstableContent) {
        this.unstableContent = unstableContent;
    }

    @DataBoundSetter
    public void setRecoveryContent(String recoveryContent) {
        this.recoveryContent = recoveryContent;
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
        Result currentResult = build.getResult();
        if (currentResult == null) {
            listener.error("WXWORK: Cannot get build result, skip notification");
            return true;
        }

        boolean shouldSend = false;
        String content = null;

        // 优先判断恢复通知
        if (notifyRecovery && currentResult == Result.SUCCESS) {
            Run<?, ?> previousRun = build.getPreviousBuild();
            if (previousRun != null) {
                Result previousResult = previousRun.getResult();
                if (previousResult != null && (previousResult == Result.FAILURE || previousResult == Result.UNSTABLE)) {
                    shouldSend = true;
                    // 使用独立 recoveryContent，未配置则回退到 successContent
                    content = (recoveryContent != null && !recoveryContent.isEmpty()) ? recoveryContent : successContent;
                }
            }
        }

        // 其他通知条件
        if (!shouldSend) {
            if (currentResult == Result.SUCCESS && notifySuccess) {
                shouldSend = true;
                content = successContent;
            } else if (currentResult == Result.FAILURE && notifyFailure) {
                shouldSend = true;
                content = failureContent;
            } else if (currentResult == Result.UNSTABLE && notifyUnstable) {
                shouldSend = true;
                content = unstableContent;
            }
        }

        if (!shouldSend) {
            listener.getLogger().println("WXWORK: No notification configured for this build result, skip");
            return true;
        }

        Set<String> atSet = FreeStyleJobHelper.parseAtSet(at);
        FreeStyleJobHelper.sendMessage(build, listener, robotSender, robot, type, content, atMe, atAll, atSet, imageUrl);
        return true;
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.WxWorkNotifier_displayName();
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
