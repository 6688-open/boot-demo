package com.dj.boot.configuration.applicationcontext;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.applicationcontext
 * @Author: wangJia
 * @Date: 2020-07-28-18-03
 */
public class CustomerApplicationContext extends AnnotationConfigServletWebServerApplicationContext {
    @Override
    protected void initPropertySources(){
        super.initPropertySources();
        //添加验证要求
        getEnvironment().setRequiredProperties("VAR_HOST");
    }
}
