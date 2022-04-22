package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public class SimpleValidatorChain implements IValidatorChain {

	private List<IValidatorPlugin> validatorPlugins;

	/**
	 * 
	 * @param plugin    plugin
	 */
	public void addPlugin(IValidatorPlugin plugin){
		if (CollectionUtils.isEmpty(validatorPlugins)) validatorPlugins = new ArrayList<IValidatorPlugin>();

		this.validatorPlugins.add(plugin);
	}

	/**
	 * 
	 * @param context    context
	 */
	public boolean validate(ComponentContext context){
		if (CollectionUtils.isEmpty(validatorPlugins)) return Boolean.TRUE;

		Iterator<IValidatorPlugin> validatorPluginIterator = validatorPlugins.iterator();

		while (validatorPluginIterator.hasNext()) {
			if(!validatorPluginIterator.next().validate(context)) return Boolean.FALSE ;
		}

		return Boolean.TRUE;
	}

	public void setValidatorPlugins(List<IValidatorPlugin> validatorPlugins) {
		this.validatorPlugins = validatorPlugins;
	}
}