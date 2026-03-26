package io.jenkins.plugins.wxwork.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * <p>消息类型</p>
 *
 * @author nekoimi 2022/07/12
 */
@Getter
public enum MessageType {
    /**
     * 文本
     */
    TEXT("文本消息", "text"),

    /**
     * markdown
     */
    MARKDOWN("Markdown消息","markdown"),

    /**
     * markdown_v2
     */
    MARKDOWN_V2("MarkdownV2消息","markdown_v2"),

    /**
     * 图片消息
     */
    IMAGE("图片消息","image");

    private final String title;

    @JsonValue
    private final String value;

    MessageType(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public static MessageType typeValueOf(String typeStr) {
        for (MessageType messageType : values()) {
            if (messageType.getValue().equalsIgnoreCase(typeStr)) {
                return messageType;
            }
        }
        return null;
    }

  @Override
    public String toString() {
        return getValue();
    }
}
