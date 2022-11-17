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
     */
    String id();

    /**
     * <p>机器人名称</p>
     *
     */
    String name();

    /**
     * <p>机器人webhook地址</p>
     *
     */
    String webhook();
}
