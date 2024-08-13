package io.jenkins.plugins.wxwork.bo;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.Run;
import hudson.model.TaskListener;
import io.jenkins.plugins.wxwork.enums.MessageType;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>RobotPipelineVars</p>
 *
 * @author nekoimi 2022/11/14
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotPipelineVars {

    /**
     * Run
     */
    Run<?, ?> run;

    /**
     * 环境变量
     */
    private EnvVars envVars;

    /**
     * workspace
     */
    private FilePath workspace;

    /**
     * taskListener
     */
    private TaskListener listener;

    /**
     * 当前执行用户信息
     */
    private RunUser runUser;

    /**
     * 机器人ID
     */
    private String robot;

    /**
     * 消息类型
     */
    @Builder.Default
    private MessageType type = MessageType.TEXT;

    /**
     * 是否"@"我自己(当前构建执行人)
     */
    @Builder.Default
    private Boolean atMe = false;

    /**
     * 是否"@"所有人
     */
    @Builder.Default
    private Boolean atAll = false;

    /**
     * "@"成员列表，填写企业微信成员手机号
     */
    @Builder.Default
    private Set<String> at = new HashSet<>();

    /**
     * Text/Markdown消息内容
     */
    @Builder.Default
    private List<String> text = new ArrayList<>();

    /**
     * Image消息图片地址
     */
    private String imageUrl;
}
