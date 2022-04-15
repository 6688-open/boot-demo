package com.dj.boot.btp.exception.domain;


import com.dj.boot.btp.exception.msg.*;

import java.util.List;
import java.util.ResourceBundle;

/**
 * 国际化消息使用帮助类
 * Time: 下午4:03
 */
public class MsgSupport {

    /** 默认资源基本包名称 */
    private static String baseName = "message";
    private static String baseCode="default.system.cs";
    /** 英文环境 */
    public static LocaleProvider enUS = new ExceptionMsg_en_US();
    /** 中文环境 */
    public static LocaleProvider zhCH = new ExceptionMsg_zh_CN();

    public static String getDefault(LocaleProvider localeProvider,String key){
        DefaultMsgProvider defaultMsgProvider=new DefaultMsgProvider(localeProvider);
        return defaultMsgProvider.getText(key);
    }

    public static String getDefault(LocaleProvider localeProvider){
        DefaultMsgProvider defaultMsgProvider=new DefaultMsgProvider(localeProvider);
        return defaultMsgProvider.getText(baseCode);
    }


    public static String getText(LocaleProvider localeProvider, String key) {
        return getTextProvider(localeProvider).getText(key);
    }


    public static String getText(LocaleProvider localeProvider, String key, String defaultValue) {
        return getTextProvider(localeProvider).getText(key, defaultValue);
    }


    public static String getText(LocaleProvider localeProvider, String key, String defaultValue, String obj) {
        return getTextProvider(localeProvider).getText(key, defaultValue, obj);
    }


    public static String getText(LocaleProvider localeProvider, String key, List<?> args) {
        return getTextProvider(localeProvider).getText(key, args);
    }


    public static String getText(LocaleProvider localeProvider, String key, String[] args) {
        return getTextProvider(localeProvider).getText(key, args);
    }

    public static String getText(LocaleProvider localeProvider, String key, String defaultValue, List<?> args) {
        return getTextProvider(localeProvider).getText(key, defaultValue, args);
    }

    public static String getText(LocaleProvider localeProvider, String key, String defaultValue, String[] args) {
        return getTextProvider(localeProvider).getText(key, defaultValue, args);
    }


    public static ResourceBundle getTexts(LocaleProvider localeProvider, String bundleName) {
        return LocalizedExpMsgUtil.findResourceBundle(baseName, localeProvider.getLocale());
    }

    private static MsgProvider getTextProvider(LocaleProvider localeProvider) {
        MsgProviderFactory tpf = new MsgProviderFactory();
        return tpf.createInstance(LocalizedExpMsgUtil.findResourceBundle(baseName, localeProvider.getLocale()), localeProvider);
    }

}
