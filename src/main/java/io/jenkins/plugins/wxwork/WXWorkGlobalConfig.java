package io.jenkins.plugins.wxwork;

import hudson.Extension;
import io.jenkins.plugins.wxwork.property.WXWorkRobotProperty;
import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import lombok.Getter;
import lombok.ToString;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.ArrayList;
import java.util.List;

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
     * `机器人` 配置页面
     *
     * @return 机器人配置页面
     */
    public WXWorkRobotProperty.WXWorkRobotPropertyDescriptor getWXWorkRobotPropertyDescriptor() {
        return Jenkins.get().getDescriptorByType(WXWorkRobotProperty.WXWorkRobotPropertyDescriptor.class);
    }
}
