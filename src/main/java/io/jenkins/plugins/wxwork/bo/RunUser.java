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
    private String name;
    private String mobile;
}
