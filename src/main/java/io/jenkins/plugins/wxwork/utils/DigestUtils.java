package io.jenkins.plugins.wxwork.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>DigestUtils</p>
 *
 * @author nekoimi 2022/07/13
 */
public class DigestUtils {

    private DigestUtils() {
    }

    public static String md5hex(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(bytes);
            if (md5Bytes == null || md5Bytes.length == 0)
                return null;
            StringBuilder mb = new StringBuilder();
            for (byte b : md5Bytes) {
                mb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return mb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
