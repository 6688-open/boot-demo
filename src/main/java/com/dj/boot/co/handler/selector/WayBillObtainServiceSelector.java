package com.dj.boot.co.handler.selector;

import com.dj.boot.co.handler.process.WayBillObtainByShipperPerformService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述:wayBill服务处理选择器
 */
@Service("wayBillObtainServiceSelector")
public class WayBillObtainServiceSelector implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, WayBillObtainByShipperPerformService> waybillServiceMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, WayBillObtainByShipperPerformService> beansMap = applicationContext.getBeansOfType(WayBillObtainByShipperPerformService.class);
        if (beansMap.size() > 0) {
            waybillServiceMap = new HashMap<>();
            for (Map.Entry<String, WayBillObtainByShipperPerformService> entry : beansMap.entrySet()) {
                waybillServiceMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    public WayBillObtainByShipperPerformService select(String performSystem) {
        String selectKey = PerformSystemWaybillServiceRelationEnum.getWaybillServiceByCode(performSystem);
        if (selectKey == null) {
            selectKey = PerformSystemWaybillServiceRelationEnum.COMMON.serviceKey;
        }
        WayBillObtainByShipperPerformService wayBillObtainService = waybillServiceMap.get(selectKey);
        return wayBillObtainService;
    }


    public enum PerformSystemWaybillServiceRelationEnum {
        PDD("1","pddObtainWayBillService","拼夕夕-服务"),
        OTHER("2","otherObtainWayBillService","其他-服务"),
        COMMON("10","commonObtainWayBillService","common-服务"),
        ;
        private String code;
        private String serviceKey;
        private String desc;

        private static Map<String, String> RELA_MAP = new HashMap<String, String>();

        static {
            EnumSet<PerformSystemWaybillServiceRelationEnum> enumSet = EnumSet.allOf(PerformSystemWaybillServiceRelationEnum.class);
            for (PerformSystemWaybillServiceRelationEnum en : enumSet) {
                RELA_MAP.put(en.getCode(), en.getServiceKey());
            }
        }

        PerformSystemWaybillServiceRelationEnum(String code, String serviceKey, String desc) {
            this.code = code;
            this.serviceKey = serviceKey;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getServiceKey() {
            return serviceKey;
        }

        public void setServiceKey(String serviceKey) {
            this.serviceKey = serviceKey;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static String getWaybillServiceByCode(String code) {
            return RELA_MAP.get(code);
        }

    }


}
