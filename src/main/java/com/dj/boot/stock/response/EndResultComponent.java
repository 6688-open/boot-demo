package com.dj.boot.stock.response;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.AbsResultComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author:Anthony
 * @date:${date}
 * 终结点组件返回结果处理
 **/
public class EndResultComponent extends AbsResultComponent {
    private static final Logger log = LogManager.getLogger(EndResultComponent.class);

    @Override
    public ResultData execute(ComponentContext context) {
        ResultData resultData = (ResultData)context.getResultMap().get(context.getCurrentComponentId());
        if (resultData == null) {
            return ResultData.successResultData(context.getBusiData());
        } else {
            log.error("组件id:{},,code:{},desc:{}",context.getCurrentComponentId(),resultData.getResultCode(),resultData.getDesc());
            return  resultData;
        }
    }
}
