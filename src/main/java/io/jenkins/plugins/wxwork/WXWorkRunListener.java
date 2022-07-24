package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import io.jenkins.plugins.wxwork.contract.RobotSender;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotSender;
import jenkins.model.Jenkins;
import lombok.extern.log4j.Log4j;

/**
 * <p>WXWorkRunListener</p>
 *
 * @author nekoimi 2022/07/24
 */
@Log4j
@Extension
public class WXWorkRunListener extends RunListener<Run<?, ?>> {

    private final RobotSender robotSender = WXWorkRobotSender.instance();

    private final String rootUrl = Jenkins.get().getRootUrl();

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        super.onStarted(run, listener);

        log.info("onStarted");
    }

    @Override
    public void onCompleted(Run<?, ?> run, @NonNull TaskListener listener) {
        super.onCompleted(run, listener);

        // TODO 可以在这里获取构建状态，做自定义操作
        // Result runResult = run.getResult();

        log.info("onCompleted");
    }
}
