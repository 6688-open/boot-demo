package com.dj.boot.configuration.kernel.component;


import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * 是否执行接口
 * @author liqingsen
 * @version 1.0
 * @created 13-????-2018 10:21:33
 */
public interface ISupport {

	 boolean isMyWork(ComponentContext context);

}