package com.dj.boot.common.constant.privacy.handler;


/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.common.constant.privacy
 * @Author: wangJia
 * @Date: 2021-11-26-17-49
 */
public abstract class AbsTypeHandler implements TypeHandler {
    protected String fillChar = "*";

    public AbsTypeHandler() {
    }

    public abstract String transfer(String var1, int var2, int var3);
}
