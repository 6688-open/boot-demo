package com.dj.boot.btp.exception.msg;

import java.util.ResourceBundle;

/**
 * 用于快速实例化一个资源提供对象（MsgProvider）
 * Time: 上午10:47
 */
public class MsgProviderFactory {

    private MsgProvider msgProvider;

    public MsgProvider createInstance(ResourceBundle bundle, LocaleProvider provider) {
        MsgProvider instance = getTextProvider();
        if (instance instanceof ResourceBundleMsgProvider) {
            ((ResourceBundleMsgProvider) instance).setBundle(bundle);
            ((ResourceBundleMsgProvider) instance).setLocaleProvider(provider);
        }
        return instance;
    }

    protected MsgProvider getTextProvider() {
        if (this.msgProvider == null) {
            return new ExpMsgProvider();
        } else {
            return msgProvider;
        }
    }

    public void setMsgProvider(MsgProvider msgProvider) {
        this.msgProvider = msgProvider;
    }
}
