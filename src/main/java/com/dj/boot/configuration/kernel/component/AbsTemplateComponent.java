package com.dj.boot.configuration.kernel.component;

import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.plugin.IFilterChain;
import com.dj.boot.configuration.kernel.plugin.IPostWorkChain;
import com.dj.boot.configuration.kernel.plugin.IValidatorChain;
import com.dj.boot.configuration.kernel.plugin.SimpleFilterChain;

import java.util.List;

/**
 * @author liqingsen
 * @version 1.0
 * @created 13-����-2018 10:21:32
 */
public abstract class AbsTemplateComponent implements IBusiComponent {

    private String id;

    private List<String> dependenceComponentIDList;
    private IFilterChain filterChain;
    private IPostWorkChain postWorkChain;
    private IValidatorChain validatorChain;
    private float version;
    public ComponentExtendAttribute componentExtendAttribute;


    public boolean execute(ComponentContext context) {
        context.setCurrentComponentId(id);
        if (!isInInstruction(context)) return Boolean.TRUE;
        if (!isMyWork(context)) return Boolean.TRUE;
        if (!validate(context)) return Boolean.FALSE;
        prefilter(context);
        if (degrade(context)) return Boolean.TRUE;
        boolean res = realWork(context);
        postWork(res, context);
        return res;

    }


    public void postWork(boolean res, ComponentContext context) {
        if (null == postWorkChain) {
            return;
        }
        postWorkChain.postWork(res, context);
    }

    public void prefilter(ComponentContext context) {
        if (null == filterChain) {
            this.filterChain = new SimpleFilterChain();
        }
        filterChain.handle(context);
    }


    @Override
    public boolean validate(ComponentContext context) {
        if (null == validatorChain) {
            return Boolean.TRUE;
        }
        return validatorChain.validate(context);
    }

    public boolean isInInstruction(ComponentContext context) {
        return Boolean.TRUE;
    }


    public void setId(String sId) {
        this.id = sId;
    }

    public String getId() {
        return id;
    }

    public void setDependenceComponentIDList(List<String> dependenceComponentIDList) {
        this.dependenceComponentIDList = dependenceComponentIDList;
    }

    public void setFilterChain(IFilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public void setPostWorkChain(IPostWorkChain postWorkChain) {
        this.postWorkChain = postWorkChain;
    }

    public void setValidatorChain(IValidatorChain validatorChain) {
        this.validatorChain = validatorChain;
    }

    public void setVersion(float version) {
        this.version = version;
    }
}