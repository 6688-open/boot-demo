package com.dj.boot.controller.other;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.naming.ldap.Control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * http://localhost:8082/myController
 * @ProjectName: demo
 * @PackageName: com.dj.boot.controller.other
 * @Author: wangJia
 * @Date: 2021-11-05-16-53
 */
@Component("/myController")
public class MyController implements Controller {
    /**
     * 时候将他们的url和对应的controller对象存到HandlerMap中的，我们在继承体系中发现，
     * BeanNameUrlHandlerMapping实现了ApplicationContextAware接口
     *
     * spring在创建BeanNameUrlHandlerMapping会调用对应的接口实现方法setApplicationContext(
     * 从容器中获取所有的bean 遍历 beanName或者别名是否存在url 如果有添加到 url和controler的Map中
     * 请求时根据 请求中的url 获取指定的handler
     *
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println("myController2");
        return null;
    }
}
