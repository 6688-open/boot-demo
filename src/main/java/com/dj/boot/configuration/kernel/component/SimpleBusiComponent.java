package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * @author liqingsen
 * @version 1.0
 * @created 13-����-2018 10:21:34
 */
public abstract class SimpleBusiComponent extends AbsTemplateComponent {

    public boolean isMyWork(ComponentContext context) {
        return Boolean.TRUE;
    }

    @Override
    public boolean degrade(ComponentContext context) {
        return Boolean.FALSE;
    }

}