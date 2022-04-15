package com.dj.boot.btp.common.util.error;


import org.apache.commons.lang3.StringUtils;

/**
 * 订单异常状态描述工具类
 * 多种状态存储在一个字段中
 */
public class ErrStatusMarkUtil {
    /** 实际值 */
    private char value[];
    /** 长度 */
    private int count;


    public ErrStatusMarkUtil() {
        this.count = 10;
        this.value = String.format("%010d", 0).toCharArray();
    }

    public ErrStatusMarkUtil(String soMark) throws Exception {
        this.count = soMark.length();
        this.value = soMark.toCharArray();
    }

    /**
     * 获取第index的值，index从1开始
     * @param index
     * @return
     */
    public char charAt(int index) {
        if ((index <= 0) || (index > count))
            throw new StringIndexOutOfBoundsException(index);
        return value[index - 1];
    }

    /**
     * 修改第index的值，index从1开始
     * @param index
     * @param a
     */
    public void inChar(int index, char a) {
        if ((index <= 0) || (index > count))
            throw new StringIndexOutOfBoundsException(index);
        value[index - 1] = a;
    }

    /**
     * 获取当前长度
     * @return
     */
    public int length() {
        return count;
    }

    /**
     * 获取当前字符
     * @return
     */
    public String toString() {
        return String.valueOf(value);
    }


    public static void main(String args[]) throws Exception {
        ErrStatusMarkUtil errStatusMarkUtil = new ErrStatusMarkUtil();
        errStatusMarkUtil.inChar(ErrStatusEnum.ERR_1_1.getB(), ErrStatusEnum.ERR_1_1.getKey());
        System.err.println(errStatusMarkUtil.toString());

        String errStatus = errStatusMarkUtil.toString();
        ErrStatusMarkUtil soErrStatusUtil = StringUtils.isBlank(errStatus) ? new ErrStatusMarkUtil() : new ErrStatusMarkUtil(errStatus);
        soErrStatusUtil.inChar(ErrStatusEnum.ERR_9_3.getB(), ErrStatusEnum.ERR_9_3.getKey());
        System.err.println(soErrStatusUtil.toString());//赋值
    }

}
