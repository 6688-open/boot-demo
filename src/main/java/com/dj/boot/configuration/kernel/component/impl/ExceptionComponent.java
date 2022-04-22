package com.dj.boot.configuration.kernel.component.impl;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.IExceptionComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: mafayun
 * Date: 2018/3/27
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionComponent implements IExceptionComponent {

    private static final Logger log = LogManager.getLogger(ExceptionComponent.class);

    @Override
    public boolean execute(ComponentContext context) {

        log.error("业务:{},组件:{} 发生异常", context.getBizNo(), context.getCurrentComponentId(), context.getExceptionData().getE());

        ResultData resultData = ResultData.failResultData("未知异常");
        context.setResultMap(resultData);
        return Boolean.TRUE;
    }
}
