package com.dj.boot.configuration.kernel.component;


import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * 降级接口
 */
public interface IDegrade {

	public boolean degrade(ComponentContext context);

}