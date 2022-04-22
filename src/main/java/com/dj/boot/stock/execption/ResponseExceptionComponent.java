package com.dj.boot.stock.execption;


import com.dj.boot.common.util.json.JsonUtil;
import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.IExceptionComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 异常统一处理
 */
public class ResponseExceptionComponent implements IExceptionComponent {
    private static final Logger log = LogManager.getLogger(ResponseExceptionComponent.class);

    @Override
    public boolean execute(ComponentContext context) {


        ResultData resultData = ResultData.failResultData("异常");
        resultData.setResultCode(100);
        context.setResultMap(resultData);
        log.error("业务:{},组件:{} 发生异常:{},resultData:{}", context.getBizNo(), context.getCurrentComponentId(), context.getExceptionData().getE(), JsonUtil.toJson(resultData));
        return Boolean.TRUE;
    }
}
