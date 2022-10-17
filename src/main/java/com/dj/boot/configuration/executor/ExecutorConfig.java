package com.dj.boot.configuration.executor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 初始化线程池
 *
 * @Author: wangjia@fescotech.com
 * @Date: 2022-10-17-11-42
 */
@Configuration
@Slf4j
public class ExecutorConfig {

    @Bean("serviceBillCompareExecutor")
    public TaskExecutor saveOperationLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors() * 2;
        executor.setCorePoolSize(processors);
        executor.setMaxPoolSize(processors);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("service-bill-compare-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        log.info("对比线程池初始化完成：{}", JSON.toJSONString(executor));
        return executor;
    }
}
