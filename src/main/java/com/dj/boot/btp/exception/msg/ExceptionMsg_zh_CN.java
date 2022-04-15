package com.dj.boot.btp.exception.msg;

import java.util.Locale;

/**
 * 中文资源描述
 * Time: 上午10:31
 */
public class ExceptionMsg_zh_CN implements LocaleProvider  {

    @Override
    public Locale getLocale() {
        return new Locale("zh", "CN");
    }
}
