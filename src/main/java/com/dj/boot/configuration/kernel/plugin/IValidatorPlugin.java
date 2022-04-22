package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public interface IValidatorPlugin {

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void setNext(IValidatorPlugin plugin);

	/**
	 * 
	 * @param context    context
	 */
	public boolean validate(ComponentContext context);

}