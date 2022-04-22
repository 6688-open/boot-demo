package com.dj.boot.configuration.kernel.execption;

import com.dj.boot.configuration.kernel.execption.enums.InsideEnum;
import com.dj.boot.configuration.kernel.execption.enums.RetryEnum;

import java.text.MessageFormat;

public class ParamResponseException extends CResponseException {

    /**
     * 系统异常code编码
     */
    private static int sysCode = 11;

    private ParamResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public enum SmallEnum {
        _10(10, "{0}参数解析错误", RetryEnum.EDIT),
        _11(11, "{0}参数类型错误", RetryEnum.EDIT),
        _12(12, "{0}参数值不合法,必须{1}", RetryEnum.EDIT),
        _13(13, "{0}参数不能为空", RetryEnum.EDIT),
        _14(14, "{0}参数长度大于{1}", RetryEnum.EDIT),
        _15(15, "{0}参数长度小于{1}", RetryEnum.EDIT),
        _16(16, "{0}参数应在{1}到{2}之间", RetryEnum.EDIT),
        _17(17, "{0}参数格式错误", RetryEnum.EDIT),
        _18(18, "{0}与{1}参数必填一个", RetryEnum.EDIT),
        _19(19, "{0}列表不能空", RetryEnum.EDIT),
        _20(20, "{0}列表长度大于{1}", RetryEnum.EDIT),
        //code 21 因前期错误被占用，不能使用
        _22(22, "{0}不能等于{1}", RetryEnum.EDIT);
        /**
         * 编码
         */
        private final int code;

        /**
         * 描述
         */
        private final String desc;
        /**
         * 是否可重试
         */
        private final RetryEnum retryEnum;

        private SmallEnum(int code, String desc, RetryEnum retryEnum) {
            this.code = code;
            this.desc = desc;
            this.retryEnum = retryEnum;
        }

    }

    /**
     * {0}参数解析错误
     *
     * @return
     */
    public static ParamResponseException parseException(String param, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._10, param);
    }


    /**
     * {0}参数类型错误
     *
     * @return
     */
    public static ParamResponseException typeException(String param, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._11, param);
    }

    /**
     * {0}参数值不合法,必须{1}
     *
     * @return
     */
    public static ParamResponseException illegalException(String param, String desc, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._12, param, desc);
    }

    /**
     * {0}参数不能为空
     *
     * @return
     */
    public static ParamResponseException notNullException(String param, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._13, param);
    }

    /**
     * {0}参数长度大于{1}
     *
     * @return
     */
    public static ParamResponseException lengthGTException(String param, String length, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._14, param, length);
    }

    /**
     * {0}参数长度小于{1}
     *
     * @return
     */
    public static ParamResponseException lengthLTException(String param, String length, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._15, param, length);
    }

    /**
     * {0}参数应在{1}到{2}之间
     *
     * @return
     */
    public static ParamResponseException betweenException(String param, String min, String max, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._16, param, min, max);
    }


    /**
     * {0}参数格式错误
     *
     * @return
     */
    public static ParamResponseException formatException(String param, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._17, param);
    }


    /**
     * {0}与{1}参数必填一个
     *
     * @return
     */
    public static ParamResponseException optionalException(String param1, String param2, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._18, param1, param2);
    }

    /**
     * {0}列表不能空
     *
     * @return
     */
    public static ParamResponseException listNotNullException(String param, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._19, param);
    }

    /**
     * {0}列表长度大于{1}
     *
     * @return
     */
    public static ParamResponseException listSizeException(String param, String length, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._20, param, length);
    }

    /**
     * {0}不能等于{1}
     *
     * @return
     */
    public static ParamResponseException notEqualsException(String param, String param2, Throwable... cause) {
        return buildException(cause, InsideEnum.INNER, SmallEnum._22, param, param2);
    }

    /**
     * 手动创建异常信息
     *
     * @param throwable  异常
     * @param insideEnum 内外系统枚举
     * @param smallEnum  异常类型
     * @param arg        格式化参数
     * @return
     */
    public static ParamResponseException createException(Throwable throwable, InsideEnum insideEnum, SmallEnum smallEnum, String... arg) {

        return buildException(new Throwable[]{throwable}, insideEnum, smallEnum, arg);
    }

    private static ParamResponseException buildException(Throwable[] throwables, InsideEnum insideEnum, SmallEnum smallEnum, String... arg) {

        int code = buildCode(insideEnum, smallEnum.retryEnum, sysCode, smallEnum.code);
        String msg = MessageFormat.format(smallEnum.desc, arg);
        Throwable cause = null;
        if (throwables != null && throwables.length > 0) {
            cause = throwables[0];
        }

        ParamResponseException sysResponseException = new ParamResponseException(builDetailMessage(code, msg), cause);
        sysResponseException.setCode(code);
        sysResponseException.setMsg(msg);
        return sysResponseException;
    }

}
