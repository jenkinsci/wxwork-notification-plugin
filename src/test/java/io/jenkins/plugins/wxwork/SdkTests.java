package io.jenkins.plugins.wxwork;

import io.jenkins.plugins.wxwork.sdk.Message;
import io.jenkins.plugins.wxwork.sdk.message.FileMessage;
import io.jenkins.plugins.wxwork.sdk.message.ImageMessage;
import io.jenkins.plugins.wxwork.sdk.message.MarkdownMessage;
import io.jenkins.plugins.wxwork.sdk.message.TextMessage;

/**
 * <p>SdkTests</p>
 *
 * @author nekoimi 2022/07/15
 */
public class SdkTests {

    public static void main(String[] args) {
        Message message = TextMessage.builder().content("context").addAt("17300000000").build();
        System.out.println(message.toJson());
        Message image = ImageMessage.builder().base64("fsdfsdf").md5("fsdfds").build();
        System.out.println(image.toJson());
        Message file = FileMessage.builder().mediaId("文件ID").build();
        System.out.println(file.toJson());
    }
}
