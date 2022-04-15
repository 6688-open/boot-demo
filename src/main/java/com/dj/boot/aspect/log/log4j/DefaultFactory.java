package com.dj.boot.aspect.log.log4j;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContext;

/**
 * 默认的log4j工厂，用于创建log4j的对象，并且捕获异常，输出警告，防止对正常业务产生影响
 * Date: 2019-02-22
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class DefaultFactory {

    private static Logger log; //尝试使用项目中使用的log4j，如出现错误输出到项目文档中，如原项目log4j2获取错误，使用DefaultLogger输出

    static {
        try {
            log = LogManager.getLogger(DefaultFactory.class);
        } catch (Throwable t) {
            log = new DefaultLogger();
            log.error("record创建log4j失败,请检查log4j与disruptor版本", t);
        }
    }


    public static Logger getLogger(Class<?> clazz) {
        Logger logger;
        try {
            logger = LogManager.getLogger(clazz);
        } catch (Throwable t) {
            log.error("record创建log4j失败,请检查log4j与disruptor版本", t);
            logger = new DefaultLogger();
        }
        return logger;
    }

    public static Logger getLogger(String s) {
        Logger logger;
        try {
            logger = LogManager.getLogger(s);
        } catch (Throwable t) {
            log.error("record创建log4j失败,请检查log4j与disruptor版本", t);
            logger = new DefaultLogger();
        }
        return logger;
    }

    public static LoggerContext getContext(boolean currentContext) {
        LoggerContext loggerContext;
        try {
            loggerContext = LogManager.getContext(currentContext);
        } catch (Throwable t) {
            log.error("record创建loggerContext失败,请检查log4j与disruptor版本", t);
            loggerContext = new DefaultLoggerContext(LogManager.class.getName());
        }
        return loggerContext;
    }

    public static LoggerContext getDefaultLoggerContext() {
        return new DefaultLoggerContext(LogManager.class.getName());
    }
}
