package io.jenkins.plugins.wxwork.sdk.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.sdk.Message;

/**
 * <p>TextMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
public class TextMessage extends AtMessage {
    /**
     * <p>文本消息体</p>
     */
    private AtMessageBody text = new AtMessageBody();

    public TextMessage() {
        super(MessageType.TEXT);
    }

    public TextMessage(AtMessageBody text) {
        super(MessageType.TEXT);
        this.text = text;
    }

    public static Builder builder() {
        return new Builder();
    }

    public AtMessageBody getText() {
        return text;
    }

    public void setText(AtMessageBody text) {
        this.text = text;
    }

    public static class Builder extends AtMessageBuilder {
        @Override
        protected Message messageBuild(AtMessageBody body) {
            return new TextMessage(body);
        }
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "msgType=" + msgType +
                ", text=" + text +
                '}';
    }
}
