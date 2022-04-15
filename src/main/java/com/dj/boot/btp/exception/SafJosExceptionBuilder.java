package com.dj.boot.btp.exception;


import com.dj.boot.btp.exception.domain.MsgSupport;
import com.dj.boot.btp.exception.domain.SafJosException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * clps接口对外统一异常构造类
 * Time: 上午10:20
 */
public class SafJosExceptionBuilder {
    private static final Logger log = LogManager.getLogger(SafJosExceptionBuilder.class);

    /**
     * 构建方法
     * @param msgKey 国际化消息key值
     */
    public static SafJosException build(String msgKey) {
        return build(null, msgKey, null, null);
    }

    /**
     * 构建方法
     * @param msgKey 国际化消息key值
     * @param cause  异常原因
     */
    public static SafJosException build(String msgKey, Throwable cause) {
        return build(null, msgKey, cause, null);
    }


    /**
     * 构建方法
     * @param systemKey 系统消息key值
     * @param msgKey    国际化消息key值
     * @exception SafJosException
     */
    public static SafJosException build(String systemKey, String msgKey) {
        return build(systemKey, msgKey, null, null);
    }

    /**
     * 构建方法
     * @param systemKey 系统消息key值
     * @param msgKey    国际化消息key值
     * @param cause     异常原因
     */
    public static SafJosException build(String systemKey, String msgKey, Throwable cause) {
        return build(systemKey, msgKey, cause, null);
    }

    /**
     * 构建方法
     * @param systemKey 系统消息key值
     * @param msgKey    国际化消息key值
     * @param params    格式化入参
     */
    public static SafJosException build(String systemKey, String msgKey, String[] params) {
        return build(systemKey, msgKey, null, params);
    }


    /**
     * 构建方法
     * @param systemKey 系统消息key值
     * @param msgKey    国际化消息key值
     * @param cause     异常原因
     * @param params    格式化入参
     */
    public static SafJosException build(String systemKey, String msgKey, Throwable cause, String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder enMsg = new StringBuilder();
        StringBuilder cnMsg = new StringBuilder();
        if (systemKey != null && systemKey.length() > 0) {
            enMsg.append(MsgSupport.getDefault(MsgSupport.enUS, systemKey)).append(" ");
            cnMsg.append(MsgSupport.getDefault(MsgSupport.zhCH, systemKey)).append(" ");
            stringBuilder.append(enMsg).append(" ");
        }else {
            enMsg.append(MsgSupport.getDefault(MsgSupport.enUS)).append(" ");
            cnMsg.append(MsgSupport.getDefault(MsgSupport.zhCH)).append(" ");
            stringBuilder.append(enMsg).append(" ");
        }
        stringBuilder.append(msgKey);
        if (params == null || params.length == 0) {//如果没有格式化
            enMsg.append(MsgSupport.getText(MsgSupport.enUS, msgKey));
            cnMsg.append(MsgSupport.getText(MsgSupport.zhCH, msgKey));
        } else {
            enMsg.append(MsgSupport.getText(MsgSupport.enUS, msgKey, params));
            cnMsg.append(MsgSupport.getText(MsgSupport.zhCH, msgKey, params));
        }
        log.error("\n  saf服务异常:code-->" + stringBuilder.toString() + ", \n   enMsg-->" + enMsg + ",\n   cnMsg-->" + cnMsg, cause);
        return new SafJosException(stringBuilder.toString(), cnMsg.toString(), enMsg.toString(), cause);
    }


}
