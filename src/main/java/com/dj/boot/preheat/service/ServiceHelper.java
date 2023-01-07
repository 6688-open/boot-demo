package com.dj.boot.preheat.service;

import com.dj.boot.service.compensate.CompensateItemService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Data
public class ServiceHelper {

    private static ServiceHelper instance;

    @Resource
    private TaskQueueCacheService taskQueueCacheService;

    @Resource
    private CompensateItemService compensateItemService;

    @PostConstruct
    private void init() {
        instance = this;
    }

    public static ServiceHelper getInstance() {
        return instance;
    }


}
