package com.dj.boot.common.constant.privacy.handler;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.common.constant.privacy
 * @Author: wangJia
 * @Date: 2021-11-26-17-50
 */
public class SimpleTypeHandler extends AbsTypeHandler {
    public SimpleTypeHandler() {
    }

    public String transfer(String source, int leftRemain, int rightRemain) {
        if (leftRemain >= 0 && rightRemain >= 0) {
            if (source != null && !"".equals(source)) {
                if (source.length() <= leftRemain) {
                    leftRemain = source.length();
                }

                if (source.length() <= rightRemain) {
                    rightRemain = source.length();
                }

                int middle = 0;
                if (source.length() > leftRemain + rightRemain) {
                    middle = source.length() - leftRemain - rightRemain;
                }

                String target = "";
                StringBuffer sb = new StringBuffer();
                if (middle <= 0) {
                    return source;
                } else {
                    sb.append(source.substring(0, leftRemain));

                    for (int i = 0; i < middle; ++i) {
                        sb.append(this.fillChar);
                    }

                    sb.append(source.substring(source.length() - rightRemain));
                    return sb.toString();
                }
            } else {
                return source;
            }
        } else {
            throw new RuntimeException("保留数字设置错误");
        }
    }

    public static void main(String[] args) {
        System.out.println((new SimpleTypeHandler()).transfer("张三", 1, 0));
    }
}
