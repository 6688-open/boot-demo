package com.dj.boot.configuration.processor.factorybean;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.configuration.processor.factorybean
 * @Author: wangJia
 * @Date: 2021-11-18-15-08
 */
public class MyFactoryBeanServiceImpl implements MyFactoryBeanService{
    @Override
    public String echo(String var) {
        System.out.println(var);
        return "You Said "+ var;
    }
}
