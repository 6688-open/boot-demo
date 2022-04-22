package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public interface IFilterChain {

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void addPlugin(IFilterPlugin plugin);

	/**
	 * 
	 * @param context    context
	 */
	public boolean handle(ComponentContext context);

}