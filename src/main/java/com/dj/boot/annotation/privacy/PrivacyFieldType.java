package com.dj.boot.annotation.privacy;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.annotation.privacy
 * @Author: wangJia
 * @Date: 2021-11-26-17-42
 */
public enum PrivacyFieldType {
    CHINESE_NAME(1, 0),
    MOBILE_PHONE(3, 4),
    ADDRESS(1, 1),
    ID_CARD(1, 1),
    EMAIL(2, 0),
    USER_ACCOUNT(1, 1),
    FIXED_PHONE(0, 0),
    BANK_CARD(4, 4),
    CNAPS_CODE(1, 1),
    ALL(0, 0);

    int leftRemain;
    int rightReamin;

    private PrivacyFieldType(int leftRemain, int rightReamin) {
        this.leftRemain = leftRemain;
        this.rightReamin = rightReamin;
    }

    public int getLeftRemain() {
        return this.leftRemain;
    }

    public void setLeftRemain(int leftRemain) {
        this.leftRemain = leftRemain;
    }

    public int getRightReamin() {
        return this.rightReamin;
    }

    public void setRightReamin(int rightReamin) {
        this.rightReamin = rightReamin;
    }
}
