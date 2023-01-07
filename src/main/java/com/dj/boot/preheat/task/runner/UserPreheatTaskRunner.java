package com.dj.boot.preheat.task.runner;

import com.alibaba.fastjson.JSON;
import com.dj.boot.preheat.domain.TaskInfo;
import com.dj.boot.preheat.service.ServiceHelper;
import com.dj.boot.preheat.service.TaskQueueCacheService;
import com.dj.boot.preheat.task.AbstractTaskRunner;
import com.dj.boot.service.compensate.CompensateItemService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class UserPreheatTaskRunner extends AbstractTaskRunner {

    public UserPreheatTaskRunner(TaskInfo taskInfo) {
        super(taskInfo);
    }

    private final TaskQueueCacheService taskQueueCacheService = ServiceHelper.getInstance().getTaskQueueCacheService();
    private final CompensateItemService compensateItemService = ServiceHelper.getInstance().getCompensateItemService();

    private final AtomicLong count = new AtomicLong(0);
    private final AtomicLong successCount = new AtomicLong(0);
    private final AtomicLong failureCount = new AtomicLong(0);
    private final int fetchCount = 2000;

    @Override
    protected void process(TaskInfo taskInfo) {
        log.info("TaskRunner 开始执行任务 taskInfo:{}", JSON.toJSONString(taskInfo));
        String dbKey = taskInfo.getDb();
        String usertable = taskInfo.getTable();
        String lastAuthUserId = null;
        //CompensateItem compensateItem = compensateItemService.getCompensateItem((long) 1);
        while (!Thread.currentThread().isInterrupted()) {
            log.info("dbKey:{}", dbKey);

            log.info(String.format("预热-查询实名单表预热结束。   dbkey[" + dbKey + "] tableindex[" + usertable + "] --count:%s, successCount:%s, failureCount:%s,",
                    count.get(), successCount.get(), failureCount.get()));
            /*if (users == null || users.size() <= 0) {
                log.info(String.format("查询实名单表预热结束。   dbkey[" + dbKey + "] tableindex[" + usertable + "] --count:%s, successCount:%s, failureCount:%s,",
                        count.get(), successCount.get(), failureCount.get()));
                //结果存入redis
                taskInfo.setTotalCount(count.get());
                taskInfo.setSuccessNum(successCount.get());
                break;
            } */
        }
        taskQueueCacheService.addDone(taskInfo);
        log.info("TaskRunner 完成执行任务 taskInfo:{}", JSON.toJSONString(taskInfo));

    }
}
