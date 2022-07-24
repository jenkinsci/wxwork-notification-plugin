package io.jenkins.plugins.wxwork.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import lombok.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>TextMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
@Getter
@ToString
public class TextMessage extends AbstractMessage {
    /**
     * <p>文本消息体</p>
     */
    private Text text = new Text();

    public TextMessage() {
        super(MessageType.TEXT);
    }

    public TextMessage(Text text) {
        super(MessageType.TEXT);
        this.text = text;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Text {
        /**
         * <p>文本内容，最长不超过2048个字节，必须是utf8编码</p>
         */
        private String content;

        /**
         * <p>手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人</p>
         */
        private Set<String> mentionedMobileList;
    }

    public static class Builder {
        private String content;
        private Set<String> mentionedMobileList;

        public Builder() {
            this.mentionedMobileList = new HashSet<>();
        }

        public Builder(String content, Set<String> mentionedMobileList) {
            this.content = content;
            this.mentionedMobileList = Optional.ofNullable(mentionedMobileList).orElse(new HashSet<>());
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder at(Set<String> mentionedMobileList) {
            this.mentionedMobileList = mentionedMobileList;
            return this;
        }

        public Builder addAt(String mobile) {
            if (mobile != null && mobile.length() > 0) {
                this.mentionedMobileList.add(mobile);
            }
            return this;
        }

        public Builder atAll() {
            this.mentionedMobileList.clear();
            this.mentionedMobileList.add("@all");
            return this;
        }

        public RobotRequest build() {
            return new TextMessage(new Text(content, mentionedMobileList));
        }
    }
}
