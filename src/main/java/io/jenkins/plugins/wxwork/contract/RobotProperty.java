package io.jenkins.plugins.wxwork.contract;

/**
 * <p>RobotProperty</p>
 *
 * @author nekoimi 2022/07/16
 */
public interface RobotProperty {

    /**
     * <p>机器人唯一标识</p>
     *
     * @return
     */
    String id();

    /**
     * <p>机器人名称</p>
     *
     * @return
     */
    String name();

    /**
     * <p>机器人webhook地址</p>
     *
     * @return
     */
    String webhook();
}
