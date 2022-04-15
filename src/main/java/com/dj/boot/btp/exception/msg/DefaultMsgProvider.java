package com.dj.boot.btp.exception.msg;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 默认消息资源提供类
 * 只用来读取default.properties
 * User: douhuatong
 * Date: 14-12-12
 * Time: 下午1:17
 */
public class DefaultMsgProvider implements MsgProvider, Serializable {
    private static final Object[] EMPTY_ARGS = new Object[0];

    /** 本地语言 */
    private LocaleProvider localeProvider;

    public DefaultMsgProvider(LocaleProvider provider) {
        this.localeProvider = provider;
    }

    @Override
    public String getText(String key) {
        return LocalizedExpMsgUtil.findDefaultMsg(key, localeProvider.getLocale());
    }

    @Override
    public String getText(String key, String defaultValue) {
        String text = getText(key);
        if (text == null) {
            return defaultValue;
        }
        return text;
    }

    @Override
    public String getText(String key, String defaultValue, String obj) {
        List<Object> args = new ArrayList<Object>(1);
        args.add(obj);
        return getText(key, defaultValue, args);
    }

    @Override
    public String getText(String key, List<?> args) {
        Object[] params;
        if (args != null) {
            params = args.toArray();
        } else {
            params = EMPTY_ARGS;
        }
        return LocalizedExpMsgUtil.findDefaultMsg(key, localeProvider.getLocale(), params);
    }

    @Override
    public String getText(String key, String[] args) {
        Object[] params;
        if (args != null) {
            params = args;
        } else {
            params = EMPTY_ARGS;
        }
        return LocalizedExpMsgUtil.findDefaultMsg(key, localeProvider.getLocale(), params);
    }

    @Override
    public String getText(String key, String defaultValue, List<?> args) {
        String text = getText(key, args);
        if (text == null && defaultValue == null) {
            defaultValue = key;
        }
        if (text == null && defaultValue != null) {
            MessageFormat format = new MessageFormat(defaultValue);
            format.setLocale(localeProvider.getLocale());
            format.applyPattern(defaultValue);
            Object[] params;
            if (args != null) {
                params = args.toArray();
            } else {
                params = EMPTY_ARGS;
            }
            return format.format(params);
        }
        return text;
    }

    @Override
    public String getText(String key, String defaultValue, String[] args) {
        String text = getText(key, args);
        if (text == null) {
            MessageFormat format = new MessageFormat(defaultValue);
            format.setLocale(localeProvider.getLocale());
            format.applyPattern(defaultValue);
            if (args == null) {
                return format.format(EMPTY_ARGS);
            }
            return format.format(args);
        }
        return text;
    }

    @Override
    public ResourceBundle getTexts(String bundleName) {
        return LocalizedExpMsgUtil.findResourceBundle(bundleName, localeProvider.getLocale());
    }
}
