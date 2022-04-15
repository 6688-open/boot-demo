package com.dj.boot.adapter;

import com.dj.boot.pojo.User;
import com.dj.boot.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component("wayBillServiceAdapterHolder")
public class WayBillServiceAdapterHolder implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, WayBillServiceAdapter> wayBillAdapterMap;

    @Resource
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        WayBillServiceEnum[] waybillServices = WayBillServiceEnum.values();
        wayBillAdapterMap = new HashMap<>(waybillServices.length);
        for (WayBillServiceEnum e : waybillServices) {
            wayBillAdapterMap.put(e.getServiceKey(), applicationContext.getBean(e.getServiceKey(), WayBillServiceAdapter.class));
        }
    }


    public WayBillServiceAdapter getBizWayBillServiceAdapterByShipper(String shipperNo) {
        if (StringUtils.isBlank(shipperNo)) {
            return getDefaultWayBillServiceAdapter();
        }
        User user = userService.getById(Integer.valueOf(shipperNo));
        if (user != null) {
            return wayBillAdapterMap.get(WayBillServiceEnum.LARGE_WAYBILL.getServiceKey());
        } else {
            return getDefaultWayBillServiceAdapter();
        }
    }

    public WayBillServiceAdapter getDefaultWayBillServiceAdapter() {
        return wayBillAdapterMap.get(WayBillServiceEnum.DEFAULT_WAYBILL.getServiceKey());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
     enum WayBillServiceEnum {
        DEFAULT_WAYBILL("wbServiceAdapter"),
        LARGE_WAYBILL("lwbServiceAdapter"),;

        private String serviceKey;

        WayBillServiceEnum(String serviceKey) {
           this.serviceKey = serviceKey;
        }

        public String getServiceKey() {
            return serviceKey;
        }

        public void setServiceKey(String serviceKey) {
            this.serviceKey = serviceKey;
        }
    }
}
