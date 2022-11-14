package io.jenkins.plugins.wxwork.bo;

import io.jenkins.plugins.wxwork.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>InputParamBo</p>
 *
 * @author nekoimi 2022/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotPipelineBo {
    /**
     * 机器人ID
     */
    private String robot;

    /**
     * 消息类型
     */
    private MessageType type = MessageType.TEXT;

    /**
     * 是否"@"我自己(当前构建执行人)
     */
    private Boolean atMe = false;

    /**
     * 是否"@"所有人
     */
    private Boolean atAll = false;

    /**
     * "@"成员列表，填写企业微信成员手机号
     */
    private Set<String> at = new HashSet<>();

    /**
     * Text/Markdown消息内容
     */
    private List<String> text = new ArrayList<>();

    /**
     * Image消息图片地址
     */
    private String imageUrl;

    /**
     * 当前执行用户信息
     */
    private RunUser runUser;

    /**
     * 当前工作绝对路径
     */
    private String runRootPath;
}
