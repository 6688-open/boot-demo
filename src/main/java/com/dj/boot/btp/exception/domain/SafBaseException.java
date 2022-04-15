package com.dj.boot.btp.exception.domain;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-01-20-17-52
 */
public class SafBaseException extends RuntimeException{
    private static final long serialVersionUID = 7952765455937049309L;

    public SafBaseException() {
    }

    public SafBaseException(String message) {
        super(message);
    }

    public SafBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SafBaseException(Throwable cause) {
        super(cause);
    }
}
