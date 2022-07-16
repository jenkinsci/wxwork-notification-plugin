package io.jenkins.plugins.wxwork.contract;

/**
 * <p>RobotResponse</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface RobotResponse {

    /**
     * <p>响应是否成功</p>
     *
     * @return
     */
    boolean isOk();

    /**
     * <p>响应异常消息</p>
     *
     * @return
     */
    String errorMessage();
}
