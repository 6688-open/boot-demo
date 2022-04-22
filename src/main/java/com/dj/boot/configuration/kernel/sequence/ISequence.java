package com.dj.boot.configuration.kernel.sequence;

import com.dj.boot.configuration.kernel.common.ResultData;
import com.dj.boot.configuration.kernel.context.ComponentContext;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-04-22-11-07
 */
public interface ISequence {
    void end();

    void init();

    ResultData start(ComponentContext var1);

    boolean validate();

    String getId();

    void setId(String var1);
}
