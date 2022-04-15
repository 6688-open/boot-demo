package com.dj.boot.service.bill.impl;

import com.dj.boot.common.base.Response;
import com.dj.boot.controller.bill.constant.Module;
import com.dj.boot.controller.bill.domain.BillExceptionRequest;
import com.dj.boot.service.bill.ExceptionService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component("exceptionService")
public class ExceptionServiceImpl implements ExceptionService, ApplicationContextAware {
    private ApplicationContext context;
    private Map<String, ExceptionService> cache;

    @Override
    public Response execute(BillExceptionRequest request) {
        return getService(request.getBillType()).execute(request);
    }

    private ExceptionService getService(String billType) {
        ExceptionService res = null;
        if (cache.containsKey(billType)) {
            res = cache.get(billType);
        } else {
            Set<Map.Entry<String, Object>> beanSet = context.getBeansWithAnnotation(Module.class).entrySet();
            Module module;
            for (Map.Entry<String, Object> stringObjectEntry : beanSet) {
                Class<?> aClass = stringObjectEntry.getValue().getClass();
                module = AnnotationUtils.findAnnotation(aClass, Module.class);
                if (module.businessType().equals(billType)) {
                    res = (ExceptionService) stringObjectEntry.getValue();
                    cache.put(billType, res);
                }
            }

        }
        if (res == null) {
            throw new RuntimeException("需传正确billType:" + billType);
        }
        return res;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cache = new ConcurrentHashMap<String, ExceptionService>();
        this.context = applicationContext;
    }


    @Override
    public Response retryException(BillExceptionRequest request) {
        return getService(request.getBillType()).retryException(request);
    }
}
