package io.jenkins.plugins.wxwork.sdk.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.sdk.Message;

/**
 * <p>MarkdownMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
public class MarkdownMessage extends AtMessage {

    /**
     * <p>markdown消息内容</p>
     */
    private AtMessageBody markdown = new AtMessageBody();

    public MarkdownMessage() {
        super(MessageType.MARKDOWN);
    }

    public MarkdownMessage(AtMessageBody markdown) {
        super(MessageType.MARKDOWN);
        this.markdown = markdown;
    }

    public static Builder builder() {
        return new Builder();
    }

    public AtMessageBody getMarkdown() {
        return markdown;
    }

    public void setMarkdown(AtMessageBody markdown) {
        this.markdown = markdown;
    }

    public static class Builder extends AtMessageBuilder {

        @Override
        protected Message messageBuild(AtMessageBody body) {
            return new MarkdownMessage(body);
        }
    }

    @Override
    public String toString() {
        return "MarkdownMessage{" +
                "msgType=" + msgType +
                ", markdown=" + markdown +
                '}';
    }
}
