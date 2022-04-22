package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * 一般组件接口
 * @author liqingsen
 * @version 1.0
 * @created 13-三月-2018 10:21:33
 */
public interface IGenericComponent extends IComponent {

	/**
	 * 
	 * @param context    context
	 */
	public boolean execute(ComponentContext context);

	public String getId();

	public void setId(String sId);
}