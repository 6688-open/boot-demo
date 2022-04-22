package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * 返回组件接口
 * @author liqingsen
 * @version 1.0
 * @created 13-????-2018 10:21:33
 */
public interface IResultComponent extends IComponent {

	/**
	 * 
	 * @param context    context
	 */
	public ResultData execute(ComponentContext context);

}