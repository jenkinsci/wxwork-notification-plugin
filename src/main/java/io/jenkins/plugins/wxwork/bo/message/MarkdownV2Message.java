package io.jenkins.plugins.wxwork.bo.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.enums.MessageType;
import lombok.*;

/**
 * <p>MarkdownMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
@Getter
@ToString
public class MarkdownV2Message extends AbstractMessage {

    /**
     * <p>markdown v2消息内容</p>
     */
    @JsonProperty("markdown_v2")
    private MarkdownV2 markdownV2 = new MarkdownV2();

    public MarkdownV2Message() {
        super(MessageType.MARKDOWN_V2);
    }

    public MarkdownV2Message(MarkdownV2 markdownV2) {
        super(MessageType.MARKDOWN_V2);
        this.markdownV2 = markdownV2;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MarkdownV2 {
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
            return new MarkdownV2Message(new MarkdownV2(content));
        }
    }
}
