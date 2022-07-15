package io.jenkins.plugins.wxwork.sdk.message;

import io.jenkins.plugins.wxwork.enums.MessageType;
import io.jenkins.plugins.wxwork.sdk.Message;

/**
 * <p>ImageMessage</p>
 *
 * @author nekoimi 2022/07/15
 */
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

    public static class Image {
        private String base64;
        private String md5;

        public Image() {
        }

        public Image(String base64, String md5) {
            this.base64 = base64;
            this.md5 = md5;
        }

        public String getBase64() {
            return base64;
        }

        public String getMd5() {
            return md5;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "base64='" + base64 + '\'' +
                    ", md5='" + md5 + '\'' +
                    '}';
        }
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

        public Message build() {
            return new ImageMessage(new Image(base64, md5));
        }
    }

    @Override
    public String toString() {
        return "ImageMessage{" +
                "msgType=" + msgType +
                ", image=" + image +
                '}';
    }
}
