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
     * <p>包装request</p>
     *
     * @param property
     * @param request
     * @return
     */
    abstract protected HttpRequest wrapRequest(@NotNull RobotProperty property, @NotNull RobotRequest request);

    /**
     * <p>包装response</p>
     *
     * @param httpResponse
     * @return
     */
    abstract protected RobotResponse wrapResponse(@NotNull HttpResponse httpResponse);

    @Override
    public RobotResponse send(@NotNull RobotProperty property, @NotNull RobotRequest request) {
        return wrapResponse(HttpCallFactory.make().call(wrapRequest(property, request)));
    }
}
