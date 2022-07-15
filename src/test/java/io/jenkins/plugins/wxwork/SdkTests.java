package io.jenkins.plugins.wxwork;

import io.jenkins.plugins.wxwork.sdk.Message;
import io.jenkins.plugins.wxwork.sdk.message.TextMessage;

/**
 * <p>SdkTests</p>
 *
 * @author nekoimi 2022/07/15
 */
public class SdkTests {

    public static void main(String[] args) {
        Message message = TextMessage.of().content("context").addAt("17300000000").build();
        System.out.println(message);
    }
}
