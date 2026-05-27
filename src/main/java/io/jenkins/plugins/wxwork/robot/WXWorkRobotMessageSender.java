package io.jenkins.plugins.wxwork.robot;

import io.jenkins.plugins.wxwork.contract.*;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotResponse;
import io.jenkins.plugins.wxwork.protocol.WXWorkRobotRequest;
import io.jenkins.plugins.wxwork.utils.JsonUtils;
import java.nio.charset.StandardCharsets;

/**
 * <p>WXWorkRobotMessageSender</p>
 *
 * @author nekoimi 2022/07/15
 */
public class WXWorkRobotMessageSender extends AbstractRobotMessageSender {

    public static RobotMessageSender instance() {
        return SingletonHolder.robotMessageSender;
    }

    private static class SingletonHolder {
        private static final RobotMessageSender robotMessageSender = new WXWorkRobotMessageSender();
    }

    @Override
    protected HttpRequest wrapRequest(RobotProperty property, RobotRequest request) {
        return new WXWorkRobotRequest(property, request);
    }

    @Override
    protected RobotResponse wrapResponse(HttpResponse httpResponse) {
        if (httpResponse.statusCode() != 200) {
            WXWorkRobotResponse response = new WXWorkRobotResponse();
            response.setErrCode(httpResponse.statusCode());
            response.setErrMsg(httpResponse.errorMessage());
            return response;
        }

        WXWorkRobotResponse response = JsonUtils.toBean(httpResponse.body(), WXWorkRobotResponse.class);
        if (response == null || response.getErrCode() == null
                ||  response.getErrMsg() == null || response.getErrMsg().isEmpty()) {
            response = new WXWorkRobotResponse();
            response.setErrCode(500);
            response.setErrMsg(httpResponse.body() != null ? new String(httpResponse.body(), StandardCharsets.UTF_8) : "响应数据解析失败");
        }
        return response;
    }
}
