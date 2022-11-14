package io.jenkins.plugins.wxwork.bo.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.contract.RobotRequest;
import lombok.*;

/**
 * <p>ImageMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
@Getter
@ToString
public class ImageMessage extends AbstractMessage {
    private Image image = new Image();

    public ImageMessage() {
        super(MessageType.IMAGE);
    }

    public ImageMessage(Image image) {
        super(MessageType.IMAGE);
        this.image = image;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Image {
        private String base64;
        private String md5;
    }

    public static class Builder {
        private String base64;
        private String md5;

        public Builder base64(String base64) {
            this.base64 = base64;
            return this;
        }

        public Builder md5(String md5) {
            this.md5 = md5;
            return this;
        }

        public RobotRequest build() {
            return new ImageMessage(new Image(base64, md5));
        }
    }
}
