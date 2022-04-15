package com.dj.boot.btp.exception.msg;


import java.util.ResourceBundle;

/**
 * 资源绑定接口
 * User: douhuatong
 * Date: 14-12-12
 * Time: 上午11:15
 */
public interface ResourceBundleMsgProvider extends MsgProvider {

    /**
     * 设置资源包
     * @param bundle
     */
    void setBundle(ResourceBundle bundle);

    /**
     * 设置语言环境
     * @param localeProvider
     */
    void setLocaleProvider(LocaleProvider localeProvider);

}
