package com.dj.boot.handlertest.biz;

import com.dj.boot.handlertest.AbstractHandlerType;
import com.dj.boot.handlertest.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SendLocationHandler
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2020-07-15-15-58
 */
@Slf4j
@Component
@HandlerType("3")
public class ReceiveLocationHandler extends AbstractHandlerType {


    @Override
    public List<String> handle(String condition) {
        System.out.println("ReceiveLocationHandler");
        List<String> list = new ArrayList<>();
        list.add("ReceiveLocationHandler");
        list.add(condition);
        return list;
    }
}
