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
import io.jenkins.plugins.wxwork.enums.MsgType;
import jenkins.tasks.SimpleBuildStep;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.Symbol;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>WXWorkBuilder</p>
 * <p>
 * 企业微信机器人文档：
 *
 * @author nekoimi 2022/07/12
 * @see <a href="https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win">https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win</a>
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
    private MsgType type;

    /**
     * "@"成员列表
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

    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener listener) throws InterruptedException, IOException {
        SimpleBuildStep.super.perform(run, workspace, env, launcher, listener);
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
