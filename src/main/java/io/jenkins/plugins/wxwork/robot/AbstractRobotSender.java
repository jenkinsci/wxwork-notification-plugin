package io.jenkins.plugins.wxwork.robot;

import com.sun.istack.internal.NotNull;
import io.jenkins.plugins.wxwork.contract.*;
import io.jenkins.plugins.wxwork.factory.HttpCallFactory;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotRequest;

/**
 * <p>AbstractRobotSender</p>
 *
 * @author nekoimi 2022/07/16
 */
public abstract class AbstractRobotSender implements RobotSender {

    /**
     * <p>包装response</p>
     *
     * @param httpResponse
     * @return
     */
    abstract protected RobotResponse wrapResponse(@NotNull HttpResponse httpResponse);

    @Override
    public RobotResponse send(@NotNull RobotProperty property, @NotNull RobotRequest request) {
        return wrapResponse(HttpCallFactory.make().call(WXWorkRobotRequest.of(property, request)));
    }
}
