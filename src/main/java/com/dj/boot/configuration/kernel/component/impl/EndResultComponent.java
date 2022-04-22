package com.dj.boot.configuration.kernel.component.impl;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.component.AbsResultComponent;
import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * @author liqingsen
 * @version 1.0
 * @created 13-����-2018 10:21:32
 */
public class EndResultComponent extends AbsResultComponent {

    public ResultData execute(ComponentContext context) {

        ResultData resultData = context.getResultMap().get(context.getCurrentComponentId());
        if (resultData == null) { //todo 不好
            return ResultData.successResultData(context.getBusiData());
        } else {
            if (resultData.getResultCode() == 200) {
                return ResultData.successResultData(context.getBusiData());
            } else {
                return resultData;
            }
        }


    }

}