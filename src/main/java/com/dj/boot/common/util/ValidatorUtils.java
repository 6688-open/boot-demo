package com.dj.boot.common.util;


import java.util.regex.Pattern;

/**
 */
public class ValidatorUtils {
    private ValidatorUtils(){}
    public static boolean validateCharSequence(String regex, CharSequence target) {
        return target != null && Pattern.compile(regex).matcher(target).matches();
    }
}
