package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public interface IPostWorkPlugin {

	/**
	 * 
	 * @param context    context
	 */
	public void postwork(boolean res, ComponentContext context);

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void setNext(IPostWorkPlugin plugin);

}