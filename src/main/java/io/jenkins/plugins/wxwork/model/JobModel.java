package io.jenkins.plugins.wxwork.model;

import io.jenkins.plugins.wxwork.utils.MarkdownColor;
import io.jenkins.plugins.wxwork.utils.StrUtils;
import lombok.*;

import java.util.Arrays;

/**
 * <p>JobMessage</p>
 *
 * @author nekoimi 2022/07/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobModel {
    /**
     * <p>项目名称</p>
     */
    private String projectName;

    /**
     * <p>项目地址</p>
     */
    private String projectUrl;

    /**
     * <p>Job名称</p>
     */
    private String jobName;

    /**
     * <p>Job访问地址</p>
     */
    private String jobUrl;

    /**
     * <p>执行人名称</p>
     */
    private String executorName;

    /**
     * <p>构建环境</p>
     */
    private String buildEnv;

    private String withBuildEnv() {
        if (StrUtils.isBlank(buildEnv)) {
            return "";
        }
        return MarkdownColor.red(buildEnv);
    }

    public String asMarkdown() {
        return String.join("\n", Arrays.asList(
                String.format("# [%s](%s)", projectName, projectUrl),
                "------------------",
                String.format("- 任务: [%s](%s)", jobName, jobUrl),
                String.format("- 构建环境: %s", withBuildEnv()),
                String.format("- 执行人: %s", executorName),
                "------------------"
        ));
    }

    public String asText() {
        return String.join("\n", Arrays.asList(
                String.format("# [%s](%s)", projectName, projectUrl),
                "------------------",
                String.format("- 任务: [%s](%s)", jobName, jobUrl),
                String.format("- 构建环境: %s", buildEnv),
                String.format("- 执行人: %s", executorName),
                "------------------"
        ));
    }
}
