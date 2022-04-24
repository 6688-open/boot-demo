package com.dj.boot.stock.filter.plugins;

import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.plugin.IFilterPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**拦截器
 * Created with IntelliJ IDEA.
 */
public class GroupLogFilterPlugin2 implements IFilterPlugin {


    private static final Logger log = LogManager.getLogger(GroupLogFilterPlugin2.class);

    @Override
    public boolean handler(ComponentContext componentContext) {
        log.info("success\t{}\t{}\tlogFilter\tsequence_biz_no\t{}\tcurrent_component_id\t{}\tdata\t{}", componentContext.getSession(), componentContext.getSequenceId(), componentContext.getBizNo(), componentContext.getCurrentComponentId(), componentContext.getBusiData()==null?"null":componentContext.getBusiData().toString());
        return Boolean.TRUE;
    }

    @Override
    public void setNext(IFilterPlugin plugin) {

    }
}
