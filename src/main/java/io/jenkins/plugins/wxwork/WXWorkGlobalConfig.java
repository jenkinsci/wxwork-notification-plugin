package io.jenkins.plugins.wxwork;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import lombok.Getter;
import lombok.ToString;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>WXWorkGlobalConfig</p>
 *
 * @author nekoimi 2022/07/24
 */
@Getter
@ToString
@Extension(ordinal = 100)
@SuppressWarnings("unused")
public class WXWorkGlobalConfig extends GlobalConfiguration {

    /**
     * <p>机器人配置列表</p>
     */
    private List<WXWorkRobotProperty> robotPropertyList = new ArrayList<>();

    public static WXWorkGlobalConfig instance() {
        return WXWorkGlobalConfig.SingletonHolder.wxWorkGlobalConfig;
    }

    private static class SingletonHolder {
        private static final WXWorkGlobalConfig wxWorkGlobalConfig =
                GlobalConfiguration.all().getInstance(WXWorkGlobalConfig.class);
    }

    public WXWorkGlobalConfig() {
        this.load();
    }

    @DataBoundConstructor
    public WXWorkGlobalConfig(List<WXWorkRobotProperty> robotPropertyList) {
        this.robotPropertyList = robotPropertyList;
    }

    @DataBoundSetter
    public void setRobotPropertyList(List<WXWorkRobotProperty> robotPropertyList) {
        this.robotPropertyList = robotPropertyList;
    }

    /**
     * <p>获取指定ID的机器人配置信息</p>
     *
     * @param robotId
     * @return
     */
    public WXWorkRobotProperty getRobotPropertyById(String robotId) {
        for (WXWorkRobotProperty robotProperty : robotPropertyList) {
            if (Objects.equals(robotProperty.getId(), robotId)) {
                return robotProperty;
            }
        }
        return null;
    }

    /**
     * `机器人` 配置页面
     *
     * @return 机器人配置页面
     */
    public WXWorkRobotProperty.WXWorkRobotPropertyDescriptor getWXWorkRobotPropertyDescriptor() {
        return Jenkins.get().getDescriptorByType(WXWorkRobotProperty.WXWorkRobotPropertyDescriptor.class);
    }
}
