package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * @author liqingsen
 * @version 1.0
 * @created 13-����-2018 10:21:32
 */
public class ConditionSemanticComponent extends AbsSemanticComponent {

	/**
	 * 
	 * @param context    context
	 */
	public boolean execute(ComponentContext context){
		return false;
	}

}