package io.jenkins.plugins.wxwork.utils;

/**
 * <p>MarkdownColor</p>
 *
 * @author nekoimi 2022/07/25
 */
public class MarkdownColor {

    private MarkdownColor() {
    }

    /**
     * <p>绿色字体消息</p>
     *
     * @param message
     * @return
     */
    public static String green(String message) {
        return "<font color=\"info\">" + message + "</font>";
    }

    /**
     * <p>灰色字体消息</p>
     *
     * @param message
     * @return
     */
    public static String grey(String message) {
        return "<font color=\"comment\">" + message + "</font>";
    }

    /**
     * <p>红色字体消息</p>
     *
     * @param message
     * @return
     */
    public static String red(String message) {
        return "<font color=\"warning\">" + message + "</font>";
    }
}
