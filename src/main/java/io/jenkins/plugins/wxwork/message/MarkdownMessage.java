package io.jenkins.plugins.wxwork.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import lombok.*;

/**
 * <p>MarkdownMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
@Getter
@ToString
public class MarkdownMessage extends AbstractMessage {

    /**
     * <p>markdown消息内容</p>
     */
    private Markdown markdown = new Markdown();

    public MarkdownMessage() {
        super(MessageType.MARKDOWN);
    }

    public MarkdownMessage(Markdown markdown) {
        super(MessageType.MARKDOWN);
        this.markdown = markdown;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Markdown {
        /**
         * <p>markdown内容，最长不超过4096个字节，必须是utf8编码</p>
         */
        private String content;
    }

    public static class Builder {
        private String content;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public RobotRequest build() {
            return new MarkdownMessage(new Markdown(content));
        }
    }
}
