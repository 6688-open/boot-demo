package com.dj.boot.btp.exception.msg;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Time: 上午11:13
 */
public class ExpMsgProvider implements ResourceBundleMsgProvider {
    private LocaleProvider localeProvider;
    private ResourceBundle bundle;

    public ExpMsgProvider() {
    }


    public ExpMsgProvider(ResourceBundle bundle, LocaleProvider provider) {
        this.bundle = bundle;
        this.localeProvider = provider;
    }

    @Override
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }


    @Override
    public void setLocaleProvider(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

    @Override
    public String getText(String key) {
        return getText(key, key, Collections.emptyList());
    }

    @Override
    public String getText(String key, String defaultValue) {
        return getText(key, defaultValue, Collections.emptyList());
    }

    @Override
    public String getText(String key, String defaultValue, String obj) {
        List<Object> args = new ArrayList<Object>();
        args.add(obj);
        return getText(key, defaultValue, args);
    }

    @Override
    public String getText(String key, List<?> args) {
        return getText(key, key, args);
    }

    @Override
    public String getText(String key, String[] args) {
        return getText(key, key, args);
    }

    public String getText(String key, String defaultValue, List<?> args) {
        Object[] argsArray = ((args != null && !args.equals(Collections.emptyList())) ? args.toArray() : null);
        return LocalizedExpMsgUtil.findText(bundle, key, getLocale(), defaultValue, argsArray);
    }

    @Override
    public String getText(String key, String defaultValue, String[] args) {
        return LocalizedExpMsgUtil.findText(bundle, key, getLocale(), defaultValue, args);
    }

    @Override
    public ResourceBundle getTexts(String bundleName) {
        return LocalizedExpMsgUtil.findResourceBundle(bundleName, getLocale());
    }

    private Locale getLocale() {
        return localeProvider.getLocale();
    }

    public LocaleProvider getLocaleProvider() {
        return localeProvider;
    }

}
