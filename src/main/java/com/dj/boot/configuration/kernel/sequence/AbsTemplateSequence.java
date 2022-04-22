package com.dj.boot.configuration.kernel.sequence;

import com.dj.boot.configuration.kernel.common.ExceptionData;
import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.IExceptionComponent;
import com.dj.boot.configuration.kernel.component.IGenericComponent;
import com.dj.boot.configuration.kernel.component.IResultComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.util.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-11-11
 */
public class AbsTemplateSequence implements ISequence{
    protected static final Logger LOGGER = LogManager.getLogger(AbsTemplateSequence.class);
    private String id;
    private List<IGenericComponent> componentChain;
    protected IExceptionComponent exceptionComponent;
    protected IResultComponent endComponent;

    public AbsTemplateSequence() {
    }

    public void init() {
    }

    public ResultData start(ComponentContext context) {
        if (!this.validate()) {
            return ResultData.failResultData("序列执行参数校验失败，请确保sequence配置参数有效");
        } else {
            context.setSequenceId(this.id);
            Iterator it = this.getComponentChain().iterator();

            try {
                ExceptionData exceptionData;
                try {
                    //execute返回false  直接跳出循环
                    while(it.hasNext() && ((IGenericComponent)it.next()).execute(context)) {
                    }
                } catch (Exception var9) {
                    exceptionData = new ExceptionData(var9);
                    context.setExceptionData(exceptionData);
                    this.exceptionComponent.execute(context);
                } catch (Throwable var10) {
                    exceptionData = new ExceptionData(new RuntimeException(var10));
                    context.setExceptionData(exceptionData);
                    this.exceptionComponent.execute(context);
                }
            } finally {
                return this.endComponent.execute(context);
            }
        }
    }

    public void end() {
    }

    public boolean validate() {
        if (CollectionUtils.isEmpty(this.componentChain)) {
            LOGGER.error("sequence validate return false ,componentChain is empty");
            return Boolean.FALSE;
        } else if (null == this.endComponent) {
            LOGGER.error("sequence validate return false ,endComponent is null");
            return Boolean.FALSE;
        } else if (null == this.exceptionComponent) {
            LOGGER.error("sequence validate return false ,exceptionComponent is null");
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    public List<IGenericComponent> getComponentChain() {
        return this.componentChain;
    }

    public void setComponentChain(List<IGenericComponent> componentChain) {
        this.componentChain = componentChain;
    }

    public IResultComponent getEndComponent() {
        return this.endComponent;
    }

    public void setEndComponent(IResultComponent endComponent) {
        this.endComponent = endComponent;
    }

    public IExceptionComponent getExceptionComponent() {
        return this.exceptionComponent;
    }

    public void setExceptionComponent(IExceptionComponent exceptionComponent) {
        this.exceptionComponent = exceptionComponent;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
