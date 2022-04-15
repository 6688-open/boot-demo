package com.dj.boot.btp.exception.msg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 对本地资源包国际化的支持工具类
 * User: douhuatong
 * Date: 14-12-12
 * Time: 上午10:55
 */
public class LocalizedExpMsgUtil {

    private static final List<String> DEFAULT_RESOURCE_BUNDLES = new CopyOnWriteArrayList<String>();
    private static final Logger log = LogManager.getLogger(LocalizedExpMsgUtil.class);
    private static final ResourceBundle EMPTY_BUNDLE = new EmptyResourceBundle();
    private static final ConcurrentMap<String, ResourceBundle> bundlesMap = new ConcurrentHashMap<String, ResourceBundle>();
    private static final ConcurrentMap<MessageFormatKey, MessageFormat> messageFormats = new ConcurrentHashMap<MessageFormatKey, MessageFormat>();
    /** 默认资源包安装路径 */
    private static final String RELOADED = "default";

    static {
        clearResourceBundles();
    }

    /** 清除资源的内部列表 */
    public static void clearResourceBundles() {
        if (DEFAULT_RESOURCE_BUNDLES != null) {
            synchronized (DEFAULT_RESOURCE_BUNDLES) {
                DEFAULT_RESOURCE_BUNDLES.clear();
                DEFAULT_RESOURCE_BUNDLES.add(RELOADED);
            }
        } else {
            synchronized (DEFAULT_RESOURCE_BUNDLES) {
                DEFAULT_RESOURCE_BUNDLES.add(RELOADED);
            }
        }
    }

    /**
     * 添加默认资源文件目录
     * @param resourceBundleName
     */
    public static void addDefaultResourceBundle(String resourceBundleName) {
        synchronized (DEFAULT_RESOURCE_BUNDLES) {
            DEFAULT_RESOURCE_BUNDLES.remove(resourceBundleName);
            DEFAULT_RESOURCE_BUNDLES.add(0, resourceBundleName);
        }
        if (log.isDebugEnabled()) {
            log.debug("成功添加默认为资源文件'" + resourceBundleName + "' 到默认的资源缓存目录= " + DEFAULT_RESOURCE_BUNDLES);
        }
    }


    /**
     * 获取默认资源信息
     * @param aTextName
     * @param locale
     * @return
     */
    public static String findDefaultMsg(String aTextName, Locale locale) {
        List<String> localList = DEFAULT_RESOURCE_BUNDLES;
        for (String bundleName : localList) {
            ResourceBundle bundle = findResourceBundle(bundleName, locale);
            if (bundle != null) {
                try {
                    return bundle.getString(aTextName);
                } catch (MissingResourceException e) {//不处理，继续下次循环
                }
            }
        }
        return null;
    }

    /**
     * 获取默认资源信息包
     * @param aTextName
     * @param locale
     * @param params
     * @return
     */
    public static String findDefaultMsg(String aTextName, Locale locale, Object[] params) {
        String defaultText = findDefaultMsg(aTextName, locale);
        if (defaultText != null) {
            MessageFormat mf = buildMessageFormat(defaultText, locale);
            return formatWithNullDetection(mf, params);
        }
        return null;
    }


    /**
     * 根据资本包路径，获取国际化资源包
     * @param aBundleName
     * @param locale
     * @return
     */
    public static ResourceBundle findResourceBundle(String aBundleName, Locale locale) {
        String key = createMissesKey(aBundleName, locale);//创建key，用于缓存当前资源包
        ResourceBundle bundle;
        try {
            if (!bundlesMap.containsKey(key)) {
                bundle = ResourceBundle.getBundle(aBundleName, locale);
                bundlesMap.putIfAbsent(key, bundle);
            }
            bundle = bundlesMap.get(key);
        } catch (MissingResourceException ex) {  //如果资源包丢失，则根据类路径加载
            try {
                if (!bundlesMap.containsKey(key)) {
                    bundle = ResourceBundle.getBundle(aBundleName, locale, LocalizedExpMsgUtil.class.getClassLoader());
                    bundlesMap.putIfAbsent(key, bundle);
                }
                bundle = bundlesMap.get(key);
            } catch (MissingResourceException e) {   //如果类路径为空，则加载空资源包，防止后续读取异常
                bundle = EMPTY_BUNDLE;
                bundlesMap.putIfAbsent(key, bundle);
            }
        }
//        return (bundle == EMPTY_BUNDLE) ? null : bundle;
        return bundle;
    }


    /**
     * 获取资源包的值
     * @param bundle
     * @param aTextName
     * @param locale
     * @param defaultMessage
     * @param args
     * @return
     */
    public static String findText(ResourceBundle bundle, String aTextName, Locale locale, String defaultMessage, Object[] args) {
        try {
            String message = bundle.getString(aTextName);
            MessageFormat mf = buildMessageFormat(message, locale);
            return formatWithNullDetection(mf, args);
        } catch (MissingResourceException ex) {
        }
        if (log.isDebugEnabled()) {
            log.warn("Unable to find text for key '" + aTextName + "' in ResourceBundles for locale '" + locale + "'");
        }
        return defaultMessage;
    }


    /**
     * null值格式化
     * @param mf
     * @param args
     * @return
     */
    private static String formatWithNullDetection(MessageFormat mf, Object[] args) {
        String message = mf.format(args);
        if ("null".equals(message)) {
            return null;
        } else {
            return message;
        }
    }

    /**
     * 根据当前语言环境，创建一个格式化对象
     * @param pattern
     * @param locale
     * @return
     */
    private static MessageFormat buildMessageFormat(String pattern, Locale locale) {
        MessageFormatKey key = new MessageFormatKey(pattern, locale);
        MessageFormat format = messageFormats.get(key);
        if (format == null) {
            format = new MessageFormat(pattern);
            format.setLocale(locale);
            format.applyPattern(pattern);
            messageFormats.put(key, format);
        }
        return format;
    }


    private static String createMissesKey(String aBundleName, Locale locale) {
        return aBundleName + "_" + locale.toString();
    }


    //***空属性
    private static class EmptyResourceBundle extends ResourceBundle {
        @Override
        public Enumeration<String> getKeys() {
            return null; // dummy
        }

        @Override
        protected Object handleGetObject(String key) {
            return null; // dummy
        }
    }

    static class MessageFormatKey {
        String pattern;
        Locale locale;

        MessageFormatKey(String pattern, Locale locale) {
            this.pattern = pattern;
            this.locale = locale;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MessageFormatKey)) return false;
            final MessageFormatKey messageFormatKey = (MessageFormatKey) o;
            if (locale != null ? !locale.equals(messageFormatKey.locale) : messageFormatKey.locale != null)
                return false;
            if (pattern != null ? !pattern.equals(messageFormatKey.pattern) : messageFormatKey.pattern != null)
                return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = (pattern != null ? pattern.hashCode() : 0);
            result = 29 * result + (locale != null ? locale.hashCode() : 0);
            return result;
        }
    }
}
