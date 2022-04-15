package com.dj.boot.configuration.permission.domain;

/**
 * 权限数据类型
 */
public enum PermissionType {
    ID('1'),
    NO('2');
    private char code;

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    PermissionType(char code) {
        this.code = code;
    }

}