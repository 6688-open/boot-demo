package com.dj.boot.aspect.log.log4j;


import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Date: 2019-02-22
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public class DefaultLoggerContext extends LoggerContext {


    public DefaultLoggerContext(String name) {
        super(name);
    }

    public org.apache.logging.log4j.Logger getDefaultLogger(String s) {
        return DefaultFactory.getLogger(s);
    }

    public Configuration getConfiguration() {
        return null;
    }
}
