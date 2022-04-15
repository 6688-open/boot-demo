package com.dj.boot.common.converter;

import com.dj.boot.common.util.date.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.common.converter
 * @Author: wangJia
 * @Date: 2021-10-19-16-03
 */
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        try {
            return DateUtil.parse(source, DateUtil.hour24HMSPattern);
        } catch (Exception e) {
            return null;
        }
    }
}
