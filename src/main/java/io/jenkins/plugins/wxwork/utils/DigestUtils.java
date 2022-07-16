package io.jenkins.plugins.wxwork.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>DigestUtils</p>
 *
 * @author nekoimi 2022/07/13
 */
public class DigestUtils {

    public static String md5hex(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            if (bytes == null || bytes.length <= 0)
                return null;
            StringBuilder mb = new StringBuilder();
            for (byte b : bytes) {
                mb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return mb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
