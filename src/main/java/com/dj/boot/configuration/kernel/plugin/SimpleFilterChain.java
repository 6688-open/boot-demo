package com.dj.boot.configuration.kernel.plugin;

import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.plugin.impl.LogFilterPlugin;
import com.dj.boot.configuration.kernel.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public class SimpleFilterChain implements IFilterChain {


    private List<IFilterPlugin> filterPlugins;

    public SimpleFilterChain() {
        this.filterPlugins = new ArrayList<IFilterPlugin>();
        /*IFilterPlugin logFilter = new LogFilterPlugin();
        filterPlugins.add(logFilter);*/
    }

    /**
     * @param plugin plugin
     */
    public void addPlugin(IFilterPlugin plugin) {

        if (CollectionUtils.isEmpty(filterPlugins)) filterPlugins = new ArrayList<IFilterPlugin>();

        this.filterPlugins.add(plugin);

    }

    /**
     * @param context context
     */
    public boolean handle(ComponentContext context) {
        if (CollectionUtils.isEmpty(filterPlugins)) return Boolean.TRUE;

        Iterator<IFilterPlugin> filterPluginIterator = filterPlugins.iterator();

        while (filterPluginIterator.hasNext()) {
            if (!filterPluginIterator.next().handler(context)) return Boolean.FALSE;
            ;
        }

        return Boolean.TRUE;
    }

    public void setFilterPlugins(List<IFilterPlugin> filterPlugins) {

        this.filterPlugins.addAll(filterPlugins);
    }
}