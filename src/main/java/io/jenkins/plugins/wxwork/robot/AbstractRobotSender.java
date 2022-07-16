package io.jenkins.plugins.wxwork.robot;

import com.sun.istack.internal.NotNull;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotSender;

/**
 * <p>AbstractRobotSender</p>
 *
 * @author nekoimi 2022/07/16
 */
public abstract class AbstractRobotSender implements RobotSender {

    /**
     * <p>包装response</p>
     *
     * @param body
     * @return
     */
    abstract protected RobotResponse wrapResponse(@NotNull String body);

    @Override
    public RobotResponse send(@NotNull RobotProperty property, @NotNull RobotRequest request) {
        return wrapResponse("");
    }
}
