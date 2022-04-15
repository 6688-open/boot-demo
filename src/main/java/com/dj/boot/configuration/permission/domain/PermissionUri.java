package com.dj.boot.configuration.permission.domain;

/**
 * 权限数据类型.
 */
public enum PermissionUri {
    dept('1'),
    seller('2'),
    warehouse('3'),
    partner('4'),
    distribute('5');
    private char code;

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    PermissionUri(char code) {
        this.code = code;
    }

}