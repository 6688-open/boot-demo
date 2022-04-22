package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public interface IFilterPlugin {

	/**
	 * 
	 * @param componentContext    componentContext
	 */
	public boolean handler(ComponentContext componentContext);

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void setNext(IFilterPlugin plugin);

}