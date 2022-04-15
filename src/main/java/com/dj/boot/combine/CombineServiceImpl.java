package com.dj.boot.combine;

import com.dj.boot.combine.dto.Command;
import com.dj.boot.combine.dto.Result;
import com.dj.boot.combine.handler.enums.HandlerType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangJia
 * @Date: 2022-04-13-10-14
 */
@Component("combineService")
public class CombineServiceImpl implements CombineService, ApplicationContextAware {

    private ApplicationContext context;
    private Map<String, CombineService> cache;

    //@InterfaceLog("#command.businessMap['businessNo']?:#command.vendorCode")
    public Result<String> execute(Command<String> command) {
        if(StringUtils.isBlank(command.getVendorCode())){
            return Result.fail("vendorCode 不允许为空");
        }
        if(StringUtils.isBlank(command.getVendorName())){
            return Result.fail("vendorName 不允许为空");
        }
        if(StringUtils.isBlank(command.getBusinessType())){
            return Result.fail("businessType 不允许为空");
        }
        if(StringUtils.isBlank(command.getOperateType())){
            return Result.fail("operateType 不允许为空");
        }
        return determineService(command).execute(command);
    }

    private CombineService determineService(Command<String> command) {
        CombineService ret = null;
        if (cache.containsKey(command.getBusinessType())) {
            ret = cache.get(command.getBusinessType());
        } else {
            Set<Map.Entry<String, Object>> beanSet = context.getBeansWithAnnotation(HandlerType.class).entrySet();
            HandlerType module;
            for (Map.Entry<String, Object> beanEntry : beanSet) {
                module = parseAnnotation(beanEntry.getValue());
                if (module.businessType().equals(command.getBusinessType())) {
                    cache.put(command.getBusinessType(), ret = (CombineService) beanEntry.getValue());
                }
            }
        }
        if (ret == null) {
            throw new RuntimeException("未找的指定businessType=" + command.getBusinessType());
        }
        return ret;
    }

    private HandlerType parseAnnotation(Object bean){
        return bean.getClass().getAnnotation(HandlerType.class);
    }
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.cache = new ConcurrentHashMap<String, CombineService>();
        this.context = context;
    }
}
