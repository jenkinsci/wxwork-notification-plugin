package io.jenkins.plugins.wxwork;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import io.jenkins.plugins.wxwork.contract.RobotProperty;
import io.jenkins.plugins.wxwork.robot.WXWorkRobotSender;
import io.jenkins.plugins.wxwork.message.TextMessage;
import io.jenkins.plugins.wxwork.utils.DigestUtils;
import io.jenkins.plugins.wxwork.utils.StrUtils;
import jenkins.model.Jenkins;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

/**
 * <p>WXWorkRobotConfig</p>
 *
 * @author nekoimi 2022/07/16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuppressWarnings("unused")
public class WXWorkRobotProperty implements Describable<WXWorkRobotProperty>, RobotProperty {

    /**
     * <p>ID，唯一</p>
     */
    private String id;

    /**
     * <p>名称</p>
     */
    private String name;

    /**
     * <p>机器人 webhook 地址</p>
     */
    private String webhook;

    @DataBoundConstructor
    public WXWorkRobotProperty(String id, String name, String webhook) {
        this.id = StrUtils.isBlank(id) ? DigestUtils.md5hex(webhook) : id;
        this.name = name;
        this.webhook = webhook;
    }

    @Override
    public Descriptor<WXWorkRobotProperty> getDescriptor() {
        return Jenkins.get().getDescriptorByType(WXWorkRobotPropertyDescriptor.class);
    }

    @Override
    public String id() {
        return StrUtils.isBlank(id) ? DigestUtils.md5hex(webhook) : id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String webhook() {
        return webhook;
    }

    @Extension
    public static class WXWorkRobotPropertyDescriptor extends Descriptor<WXWorkRobotProperty> {

        /**
         * <p>ID验证</p>
         *
         * @param id
         * @return
         */
        public FormValidation doCheckId(@QueryParameter String id) {
//            if (StrUtils.isBlank(id)) {
//                return FormValidation.error("机器人ID不能为空");
//            }
            return FormValidation.ok();
        }

        /**
         * <p>名称验证</p>
         *
         * @param name
         * @return
         */
        public FormValidation doCheckName(@QueryParameter String name) {
            if (StrUtils.isBlank(name)) {
                return FormValidation.error("机器人名称不能为空");
            }
            return FormValidation.ok();
        }

        /**
         * <p>webhook地址验证</p>
         *
         * @param webhook
         * @return
         */
        public FormValidation doCheckWebhook(@QueryParameter String webhook) {
            if (StrUtils.isBlank(webhook)) {
                return FormValidation.error("机器人webhook地址不能为空");
            }
            return FormValidation.ok();
        }

        /**
         * <p>测试机器人</p>
         *
         * @param id
         * @param name
         * @param webhook
         * @return
         */
        public FormValidation doTest(@QueryParameter("id") String id, @QueryParameter("name") String name, @QueryParameter("webhook") String webhook) {
            RobotProperty property = new WXWorkRobotProperty(id, name, webhook);
            RobotRequest message = TextMessage.builder().content("企业微信机器人测试成功").atAll().build();
            RobotResponse robotResponse = WXWorkRobotSender.instance().send(property, message);
            if (robotResponse != null && robotResponse.isOk()) {
                // ok
                String rootUrl = Jenkins.get().getRootUrl();
                return FormValidation.respond(FormValidation.Kind.OK, "<img src='" + rootUrl + "/images/16x16/accept.png'>" + "<span style='padding-left:4px;color:#52c41a;font-weight:bold;'>测试成功</span>");
            } else {
                return FormValidation.error(robotResponse.errorMessage());
            }
        }
    }
}
