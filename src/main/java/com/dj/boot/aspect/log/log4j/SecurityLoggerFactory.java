package com.dj.boot.aspect.log.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;
import java.nio.charset.Charset;


/**
 * 追加安全部规定日志
 * Date: 2018/5/3
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class SecurityLoggerFactory {

    private static final Logger log = DefaultFactory.getLogger(SecurityLoggerFactory.class);

    //输出日志静态变量
    private static String PATTERN = "%m%n";
    private static Charset CHARSET = Charset.forName("Utf-8");
    private static String FILEPATH = "/export/Logs/user-record";
    private static String FILENAME = "/user-security.log";
    private static String FILENAMEPATTERN = "/user-security-%i.log.gz";
    private static String APPENDERNAME = "securityFile";
    private static String FILESIZE = "500MB";
    private static String MAX = "3";
    private static String LOGGERNAME = "user-security";
    private static Level LEVER = Level.INFO;

    //log配置
    private static  LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    private static final Configuration config = ctx.getConfiguration();

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
                ctx.updateLoggers();
            }catch (Throwable t){
                log.error("record配置Configuration失败,请检查log4j与disruptor版本", t);
                ctx = (LoggerContext) DefaultFactory.getDefaultLoggerContext();
            }
        }else {
            log.error("record创建loggerContext失败,请检查log4j与disruptor版本");
        }
    }

    public static Logger getLogger() {
        if (ctx instanceof DefaultLoggerContext){
            return ((DefaultLoggerContext) ctx).getDefaultLogger(LOGGERNAME);
        }else {
            return ctx.getLogger(LOGGERNAME);
        }
    }
}
