package io.jenkins.plugins.wxwork.protocol;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.jenkins.plugins.wxwork.contract.RobotResponse;
import lombok.*;

/**
 * <p>SendResponse</p>
 *
 * @author nekoimi 2022/07/16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WXWorkRobotResponse implements RobotResponse {

    /**
     * <p>响应业务码</p>
     */
    @JsonAlias("errcode")
    private Integer errCode;

    /**
     * <p>异常消息</p>
     */
    @JsonAlias("errmsg")
    private String errMsg;

    @Override
    public boolean isOk() {
        return errCode != null && errCode == 0;
    }

    @Override
    public String errorMessage() {
        return "[" + errCode + "] " + errMsg;
    }
}
