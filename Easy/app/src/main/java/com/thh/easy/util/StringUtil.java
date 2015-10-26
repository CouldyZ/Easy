package com.thh.easy.util;

/**
 * Created by taCi on 2015/10/26.
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 检查字符串是否符合登陆规范
     * @param value
     * @return
     */
    public static boolean checkString(String value) {
        return value.length() >= 6 && value.length() <= 16;
    }
}
