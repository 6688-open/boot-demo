package com.dj.boot.test.mask;

import com.alibaba.fastjson.JSONObject;
import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.common.util.mask.MaskService;
import com.dj.boot.pojo.User;

import javax.annotation.Resource;

public class MaskTest extends BootDemoApplicationTests {

    @Resource
    private MaskService maskService;

    @Override
    public void run() throws Exception {


        User user = new User();
        user.setUserName("张三");
        user.setPhone("18351867657");

        User o = (User) maskService.maskFor(user);
        logger.info("user:{}", JSONObject.toJSONString(user));
        logger.info("o:{}", JSONObject.toJSONString(o));

    }
}
