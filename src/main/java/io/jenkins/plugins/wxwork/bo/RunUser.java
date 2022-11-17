package io.jenkins.plugins.wxwork.bo;

import lombok.*;

/**
 * <p>RunUser</p>
 *
 * @author nekoimi 2022/07/24
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunUser {
    /**
     * <p>当前执行人名称</p>
     */
    private String name;

    /**
     * <p>当前执行人配置的手机号</p>
     */
    private String mobile;
}
