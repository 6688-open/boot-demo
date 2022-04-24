package com.dj.boot.configuration.kernel.config;

import com.dj.boot.configuration.kernel.praser.ComponentParser;
import com.dj.boot.configuration.kernel.praser.SequenceParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.configuration.kernel.config
 * @Author: wangJia
 * @Date: 2022-04-22-10-18
 */
public class KernelNamespaceHandler extends NamespaceHandlerSupport {

    public KernelNamespaceHandler() {
    }

    public void init() {
        this.registerBeanDefinitionParser("component", new ComponentParser());
        this.registerBeanDefinitionParser("sequence", new SequenceParser());
    }
}
