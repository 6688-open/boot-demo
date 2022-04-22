package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

import java.util.List;

/**
 * @author liqingsen
 * @version 1.0
 * @created 13-����-2018 10:21:32
 */
public abstract class CombineBusiComponent extends AbsTemplateComponent {

	private List<IBusiComponent> componentList;

	public boolean degrade(ComponentContext context){
		return false;
	}


}