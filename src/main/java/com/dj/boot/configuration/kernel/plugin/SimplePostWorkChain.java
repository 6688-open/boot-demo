package com.dj.boot.configuration.kernel.plugin;


import com.dj.boot.configuration.kernel.context.ComponentContext;
import com.dj.boot.configuration.kernel.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public class SimplePostWorkChain implements IPostWorkChain {

    private List<IPostWorkPlugin> postWorkPlugins;

    /**
     * @param plugin plugin
     */
    public void addPlugin(IPostWorkPlugin plugin) {
        if (CollectionUtils.isEmpty(postWorkPlugins)) postWorkPlugins = new ArrayList<IPostWorkPlugin>();

        this.postWorkPlugins.add(plugin);

    }

    /**
     * @param context context
     */
    public void postWork(boolean res, ComponentContext context) {
        if (CollectionUtils.isEmpty(postWorkPlugins)) return;

        Iterator<IPostWorkPlugin> postWorkPluginIterator = postWorkPlugins.iterator();

        while (postWorkPluginIterator.hasNext()) {
            postWorkPluginIterator.next().postwork(res,context);
        }

    }

    public void setPostWorkPlugins(List<IPostWorkPlugin> postWorkPlugins) {
        this.postWorkPlugins = postWorkPlugins;
    }
}