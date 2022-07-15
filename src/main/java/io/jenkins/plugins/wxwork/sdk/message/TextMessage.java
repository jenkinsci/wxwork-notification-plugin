package io.jenkins.plugins.wxwork.sdk.message;

import io.jenkins.plugins.wxwork.enums.MessageType;

import java.util.*;

/**
 * <p>TextMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
public class TextMessage extends AbstractMessage {
    /**
     * <p>文本消息体</p>
     */
    private Text text = new Text();

    public TextMessage() {
        super();
    }

    public TextMessage(Text text) {
        super();
        this.text = text;
    }

    @Override
    protected MessageType messageType() {
        return MessageType.TEXT;
    }

    public static Builder of() {
        return new Builder();
    }

    public static class Text {
        /**
         * <p>文本内容，最长不超过2048个字节，必须是utf8编码</p>
         */
        private String content;

        /**
         * <p>手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人</p>
         */
        private Set<String> mentionedMobileList;

        public Text() {
        }

        public Text(String content, Set<String> mentionedMobileList) {
            this.content = content;
            this.mentionedMobileList = mentionedMobileList;
        }

        public String getContent() {
            return content;
        }

        public Set<String> getMentionedMobileList() {
            return mentionedMobileList;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setMentionedMobileList(Set<String> mentionedMobileList) {
            this.mentionedMobileList = mentionedMobileList;
        }

        @Override
        public String toString() {
            return "Text{" +
                    "content='" + content + '\'' +
                    ", mentionedMobileList=" + mentionedMobileList +
                    '}';
        }
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
            this.mentionedMobileList.add(mobile);
            return this;
        }

        public TextMessage build() {
            return new TextMessage(new Text(content, mentionedMobileList));
        }
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "msgType='" + msgType + '\'' +
                ", text=" + text +
                '}';
    }
}
