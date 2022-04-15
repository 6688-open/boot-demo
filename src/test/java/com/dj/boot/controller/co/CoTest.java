package com.dj.boot.controller.co;

import com.dj.boot.BootDemoApplicationTests;
import com.dj.boot.co.handler.common.Handler;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-03-25-11-27
 */
public class CoTest extends BootDemoApplicationTests {

    @Autowired
    private Handler handler;

    @Override
    public void run() throws Exception {
        Request request = new Request();
        Response response = new Response();
        handler.handle(request, response);
    }
}
