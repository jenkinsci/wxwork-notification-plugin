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
     * 图片消息
     */
    IMAGE("image"),

    /**
     * 图文消息
     */
    NEWS("news"),

    /**
     * 文件消息
     */
    FILE("file"),

    /**
     * 模板卡片
     */
    TEMPLATE_CARD("template_card");

    @JsonValue
    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
