package io.jenkins.plugins.wxwork.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>UploadResponse</p>
 *
 * @author nekoimi 2022/07/16
 */
@Getter
@Setter
@ToString
public class UploadResponse extends SendResponse {

    /**
     * <p>file</p>
     */
    private String type;

    /**
     * <p>文件ID</p>
     */
    private String mediaId;
}
