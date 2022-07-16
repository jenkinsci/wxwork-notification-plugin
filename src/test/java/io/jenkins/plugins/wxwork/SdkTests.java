package io.jenkins.plugins.wxwork;

import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.message.FileMessage;
import io.jenkins.plugins.wxwork.message.ImageMessage;
import io.jenkins.plugins.wxwork.message.TextMessage;
import io.jenkins.plugins.wxwork.utils.DigestUtils;

/**
 * <p>SdkTests</p>
 *
 * @author nekoimi 2022/07/15
 */
public class SdkTests {

    public static void main(String[] args) {
        RobotRequest message = TextMessage.builder().content("context").addAt("17300000000").build();
        System.out.println(message.toJson());
        RobotRequest image = ImageMessage.builder().base64("fsdfsdf").md5("fsdfds").build();
        System.out.println(image.toJson());
        RobotRequest file = FileMessage.builder().mediaId("文件ID").build();
        System.out.println(file.toJson());

        String md5hex = DigestUtils.md5hex("1");
        System.out.println(md5hex);
    }
}
