package com.dj.boot.btp.exception.msg;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * Time: 上午10:32
 */
public class ExceptionMsg_en_US implements LocaleProvider {

    @Override
    public Locale getLocale() {
        return new Locale("en", "US");
    }
}
