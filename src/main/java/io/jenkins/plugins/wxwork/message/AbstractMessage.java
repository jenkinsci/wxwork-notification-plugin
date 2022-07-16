package io.jenkins.plugins.wxwork.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import io.jenkins.plugins.wxwork.utils.JsonUtils;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>AbstractMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
@Getter
public abstract class AbstractMessage implements RobotRequest {
    /**
     * <p>消息类型</p>
     */
    @JsonProperty("msgtype")
    protected final MessageType msgType;

    public AbstractMessage(MessageType msgType) {
        this.msgType = msgType;
    }

    @Override
    public MessageType messageType() {
        return msgType;
    }

    @Override
    public String toJson() {
        return JsonUtils.toJson(this);
    }

    @Override
    public byte[] toBytes() {
        return Optional.ofNullable(toJson()).orElse("").getBytes(StandardCharsets.UTF_8);
    }
}
