package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

public abstract  class AbsSemanticComponent implements ISemanticComponent {


	public boolean execute(ComponentContext context){
		return false;
	}

}