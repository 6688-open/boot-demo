package com.dj.boot.common.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * 参数验证工具类
 *
 * @author wangjia
 * @date 2022-07-12 14:37
 */
@Component
public class Validators {

    @Resource
    private Validator validator;

    public void validate(Object target) throws Exception {
        BindException bindException = new BindException(target, target.toString());
        validator.validate(target, bindException);
        if (bindException.hasErrors()) {
            StringBuilder message = new StringBuilder();
            message.append(bindException.getFieldError().getDefaultMessage());
            message.append("[");
            message.append(bindException.getFieldError().getField());
            message.append("]");
            throw new Exception(message.toString());
        }
    }

}
