package com.dj.boot.btp.exception.msg;

import java.util.List;
import java.util.ResourceBundle;

/**
 * 国际化资源消息提供接口
 * Time: 上午10:48
 */
public interface MsgProvider {

    /**
     * 根据key，获取当前的资源包的值
     * @param key
     * @return
     */
    String getText(String key);

    /**
     * 根据key，获取当前资源包，
     * 如果key不存在，则返回 defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    String getText(String key, String defaultValue);

    /**
     * 根据key，得到一个消息 ，基于使用所提供的参数的关键
     * 如果key不存在，则返回 defaultValue
     * @param key
     * @param defaultValue
     * @param obj          用MessageFormat，进行消息格式化
     * @return
     */
    String getText(String key, String defaultValue, String obj);

    /**
     * 根据key，得到一个消息，
     * 基于使用所提供的参数的关键
     * @param key
     * @param args
     * @return
     */
    String getText(String key, List<?> args);

    /**
     * 根据key，得到一个消息，
     * 基于使用所提供的参数的关键
     * @param key
     * @param args
     * @return
     */
    String getText(String key, String[] args);

    /**
     * 根据key，得到一个消息，
     * 基于使用所提供的参数的关键
     * 如果key不存在，则返回 defaultValue
     * @param key
     * @param defaultValue
     * @param args
     * @return
     */
    String getText(String key, String defaultValue, List<?> args);

    /**
     * 根据key，得到一个消息，
     * 基于使用所提供的参数的关键
     * 如果key不存在，则返回 defaultValue
     * @param key
     * @param defaultValue
     * @param args
     * @return
     */
    String getText(String key, String defaultValue, String[] args);


    ResourceBundle getTexts(String bundleName);

}
