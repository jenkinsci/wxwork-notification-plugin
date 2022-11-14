package io.jenkins.plugins.wxwork.utils;

/**
 * <p>StrUtils</p>
 *
 * @author nekoimi 2022/07/16
 */
public class StrUtils {

    private StrUtils() {
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
