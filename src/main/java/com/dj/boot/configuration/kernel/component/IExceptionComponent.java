package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * Created with IntelliJ IDEA.
 */
public interface IExceptionComponent extends IComponent {

    public boolean execute(ComponentContext context);
}
