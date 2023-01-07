package com.dj.boot.preheat.task;

import com.dj.boot.preheat.common.Constants;
import com.dj.boot.preheat.domain.TaskInfo;
import com.dj.boot.preheat.service.ServiceHelper;
import com.dj.boot.preheat.task.runner.UserPreheatTaskRunner;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class TaskScanner extends Thread {

    private String type;

    private ThreadPoolExecutor threadPool;

    public TaskScanner(String type, int threadNum) {
        super("TaskScanner-" + type);
        this.type = type;
        threadPool = new ThreadPoolExecutor(threadNum, threadNum, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue(10));
    }

    @Override
    public void run() {
        while (true) {
            try {
                TaskInfo taskInfo = null;
                try {
                    taskInfo = ServiceHelper.getInstance().getTaskQueueCacheService().nextTask(type);
                    if (taskInfo == null) {
                        sleep();
                        continue;
                    }
                    TaskRunner taskRunner = createTaskRunner(taskInfo);
                    threadPool.submit(taskRunner);
                } catch (Exception e) {
                    // 线程池拒绝，丢回队列
                    log.info("任务提交失败,丢回队列！{}", taskInfo);
                    if (taskInfo != null) {
                        ServiceHelper.getInstance().getTaskQueueCacheService().addBackTask(taskInfo);
                    }
                    sleep();
                }
            } catch (Exception e) {
                log.error("任务调度异常！", e);
            }
        }
    }

    public void sleep() {
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private TaskRunner createTaskRunner(TaskInfo taskInfo) {
        switch (taskInfo.getType()) {
            case Constants.TASK_TYPE_NATIONALITY:
                return new UserPreheatTaskRunner(taskInfo);
        }
        return null;
    }
}
