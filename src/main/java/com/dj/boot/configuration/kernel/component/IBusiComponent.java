package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * 基础运行组件接口
 */
public interface IBusiComponent extends ISupport, IGenericComponent, IDegrade {

    public void postWork(boolean res, ComponentContext context);

    public boolean validate(ComponentContext context);

    public void prefilter(ComponentContext context);

    public boolean realWork(ComponentContext context);

    public boolean isInInstruction(ComponentContext context);
}