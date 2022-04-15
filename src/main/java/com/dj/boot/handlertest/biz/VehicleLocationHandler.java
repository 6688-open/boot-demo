package com.dj.boot.handlertest.biz;

import com.dj.boot.handlertest.AbstractHandlerType;
import com.dj.boot.handlertest.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * VehicleLocationHandler
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2020-07-15-15-58
 */
@Slf4j
@Component
@HandlerType("2")
public class VehicleLocationHandler extends AbstractHandlerType {


    @Override
    public List<String> handle(String condition) {
        System.out.println("VehicleLocationHandler");
        List<String> list = new ArrayList<>();
        list.add("VehicleLocationHandler");
        list.add(condition);
        return list;
    }
}
