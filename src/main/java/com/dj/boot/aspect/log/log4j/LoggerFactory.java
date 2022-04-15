package com.dj.boot.aspect.log.log4j;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDisruptor;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 对默认的日志配置进行追加配置
 * User: mafayun
 * Date: 2018/5/3
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class LoggerFactory {

    private static final Logger log = DefaultFactory.getLogger(LoggerFactory.class);

    //输出日志静态变量
    private static String PATTERN = "%d{yyyy-MM-dd HH:mm:ss}\001[%t][${ctx:trace-id}]\001%m%n";
    private static Charset CHARSET = Charset.forName("Utf-8");
    private static String FILEPATH = "/export/Logs/user-record";
    private static String FILENAME = "/user-record.log";
    private static String FILENAMEPATTERN = "/user-record-%i.log.gz";
    private static String APPENDERNAME = "recordFile";
    private static String FILESIZE = "500MB";
    private static String MAX = "3";
    private static String LOGGERNAME = "com.dj.boot.record";
    private static Level LEVER = Level.INFO;

    //log配置
    private static  LoggerContext ctx = (LoggerContext) DefaultFactory.getContext(false);
    private static final  Configuration config = ctx.getConfiguration();

    static {
        if (config != null) {
            try {
                //创建文件夹
                File file = new File(FILEPATH);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdir();
                }

                //追加新的配置
                //创建文件样式
                Layout layout = PatternLayout.newBuilder().
                        withConfiguration(config).
                        withPattern(PATTERN).
                        withCharset(CHARSET).build();

                //单个日志文件大小
                //TimeBasedTriggeringPolicy tbtp = TimeBasedTriggeringPolicy.newBuilder().build();
                TriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy(FILESIZE);
                CompositeTriggeringPolicy policyComposite = CompositeTriggeringPolicy.createPolicy(tp);
                DefaultRolloverStrategy strategy = DefaultRolloverStrategy.newBuilder().withMax(MAX).withConfig(config).build();

                RollingFileAppender.Builder rBuilder = RollingFileAppender.newBuilder();
                rBuilder.withName(APPENDERNAME);
                rBuilder.withFileName(FILEPATH + FILENAME);
                rBuilder.withFilePattern(FILEPATH + FILENAMEPATTERN);
                rBuilder.setConfiguration(config);
                rBuilder.withLayout(layout);
                rBuilder.withAppend(Boolean.TRUE);
                rBuilder.withStrategy(strategy);
                rBuilder.withPolicy(policyComposite);
                Appender appender = rBuilder.build();
                appender.start();
                config.addAppender(appender);


                AppenderRef ref = AppenderRef.createAppenderRef(APPENDERNAME, null, null);
                AppenderRef[] refs = new AppenderRef[]{ref};
                LoggerConfig loggerConfig = AsyncLoggerConfig.createLogger(Boolean.FALSE,
                        LEVER, LOGGERNAME, Boolean.TRUE.toString(), refs, null, config, null);
                loggerConfig.addAppender(appender, null, null);

                config.addLogger(LOGGERNAME, loggerConfig);

                //从2.9.x升级到2.14.x,及后续修复2.14及以前的版本存在远程侵入的漏洞
                //在升级过程中，异步方式存在差异，2.14版本中，维护了一个全局的AsyncLoggerConfigDelegate，当原应用配置中不包含异步配置
                //在此追加的AsyncLoggerConfig无法使用，需要手动启动syncLoggerConfigDelegate（即AsyncLoggerConfigDisruptor，默认只有这一个实现）
                AsyncLoggerConfigDelegate asyncLoggerConfigDelegate = config.getAsyncLoggerConfigDelegate();
                if(asyncLoggerConfigDelegate instanceof AsyncLoggerConfigDisruptor){
                    AsyncLoggerConfigDisruptor asyncLoggerConfigDisruptor = (AsyncLoggerConfigDisruptor) asyncLoggerConfigDelegate;
                    if(asyncLoggerConfigDisruptor.getState() != LifeCycle.State.STARTED){
                        asyncLoggerConfigDisruptor.start();
                    }
                }

                ctx.updateLoggers();
            }catch (Throwable t){
                log.error("record配置Configuration失败,请检查log4j与disruptor版本", t);
                ctx = (LoggerContext) DefaultFactory.getDefaultLoggerContext();
            }
        }else {
            log.error("record创建loggerContext失败,请检查log4j与disruptor版本");
        }
    }


    public static Logger getLogger(String className) {
        if (ctx instanceof DefaultLoggerContext){
            return ((DefaultLoggerContext) ctx).getDefaultLogger(className);
        }else {
            return ctx.getLogger(className);
        }

    }

    public static Logger getLogger() {
        return getLogger(LOGGERNAME);
    }
}
