package io.jenkins.plugins.wxwork.sdk.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.sdk.Message;
import lombok.*;

/**
 * <p>FileMessage</p>
 *
 * @author nekoimi 2022/07/16
 */
@Getter
@ToString
public class FileMessage extends AbstractMessage {

    private File file = new File();

    public FileMessage() {
        super(MessageType.FILE);
    }

    public FileMessage(File file) {
        super(MessageType.FILE);
        this.file = file;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class File {
        private String mediaId;
    }

    public static class Builder {
        private String mediaId;

        public Builder mediaId(String mediaId) {
            this.mediaId = mediaId;
            return this;
        }

        public Message build() {
            return new FileMessage(new File(mediaId));
        }
    }
}
