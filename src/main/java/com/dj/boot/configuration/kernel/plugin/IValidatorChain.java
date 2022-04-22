package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public interface IValidatorChain {

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void addPlugin(IValidatorPlugin plugin);

	/**
	 * 
	 * @param context    context
	 */
	public boolean validate(ComponentContext context);

}