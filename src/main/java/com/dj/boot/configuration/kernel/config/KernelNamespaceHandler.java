package com.dj.boot.configuration.kernel.config;

import com.dj.boot.configuration.kernel.praser.ComponentPraser;
import com.dj.boot.configuration.kernel.praser.SequencePraser;
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
        this.registerBeanDefinitionParser("component", new ComponentPraser());
        this.registerBeanDefinitionParser("sequence", new SequencePraser());
    }
}
