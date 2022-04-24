package com.dj.boot.stock.filter;

import com.dj.boot.configuration.kernel.plugin.IFilterPlugin;
import com.dj.boot.configuration.kernel.plugin.SimpleFilterChain;
import com.dj.boot.stock.filter.plugins.GroupLogFilterPlugin;
import com.dj.boot.stock.filter.plugins.GroupLogFilterPlugin2;


/**
 * 拦截器链
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-24-11-40
 */
public class LogFilterChain extends SimpleFilterChain {

    public LogFilterChain(){
        IFilterPlugin logFilterPlugin = new GroupLogFilterPlugin();
        IFilterPlugin logFilterPlugin2 = new GroupLogFilterPlugin2();
        super.addPlugin(logFilterPlugin);
        super.addPlugin(logFilterPlugin2);
    }
}
