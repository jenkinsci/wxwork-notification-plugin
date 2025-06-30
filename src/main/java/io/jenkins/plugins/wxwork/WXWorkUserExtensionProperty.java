package io.jenkins.plugins.wxwork;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import hudson.Extension;
import hudson.model.User;
import hudson.model.UserProperty;
import hudson.model.UserPropertyDescriptor;
import lombok.Getter;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest2;

/**
 * <p>Jenkins用户扩展信息</p>
 * <p>
 *
 *
 * @author nekoimi 2022/07/12
 */
@Getter
public class WXWorkUserExtensionProperty extends UserProperty {
    /**
     * 扩展企业微信用户手机号信息，支持使用手机号"@"企业微信用户
     */
    private final String mobile;

    public WXWorkUserExtensionProperty(String mobile) {
        this.mobile = mobile;
    }

    public static WXWorkUserExtensionProperty of(String mobile) {
        return new WXWorkUserExtensionProperty(mobile);
    }

    @Extension(ordinal = 1)
    public static final class WXWorkUserExtensionPropertyDescriptor extends UserPropertyDescriptor {

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.UserProperty_mobile();
        }

        @Override
        public UserProperty newInstance(User user) {
            return WXWorkUserExtensionProperty.of(null);
        }

        @Override
        public UserProperty newInstance(@Nullable StaplerRequest2 req, @NonNull JSONObject formData) throws FormException {
            return WXWorkUserExtensionProperty.of(formData.optString("mobile"));
        }
    }
}
