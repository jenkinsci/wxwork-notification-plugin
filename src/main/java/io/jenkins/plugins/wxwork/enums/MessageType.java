package io.jenkins.plugins.wxwork.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>消息类型</p>
 *
 * @author nekoimi 2022/07/12
 */
public enum MessageType {
    /**
     * 文本
     */
    TEXT("text"),

    /**
     * markdown
     */
    MARKDOWN("markdown"),

    /**
     * markdown_v2
     */
    MARKDOWN_V2("markdown_v2"),

    /**
     * 图片消息
     */
    IMAGE("image");

    @JsonValue
    private final String value;

    MessageType(String value) {
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

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
