package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;
public interface IPostWorkChain {

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void addPlugin(IPostWorkPlugin plugin);

	/**
	 * 
	 * @param context    context
	 */
	public void postWork(boolean res, ComponentContext context);

}