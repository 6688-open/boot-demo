package com.dj.boot.preheat.service;

import com.alibaba.fastjson.JSON;
import com.dj.boot.common.redis.RedisService;
import com.dj.boot.preheat.common.CacheKey;
import com.dj.boot.preheat.common.Constants;
import com.dj.boot.preheat.domain.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务队列管理服务
 */
@Slf4j
@Service
public class TaskQueueCacheService {

    @Autowired
    private RedisService redisService;

    private Long todoTaskNum(String type) {
        return redisService.lLen(CacheKey.getTaskTodoKey(type));
    }

    private Long doneTaskNum(String batchNo) {
        return redisService.hLen(CacheKey.getTaskDoneKey(batchNo));
    }

    private Long doingTaskNum(String type) {
        return redisService.hLen(CacheKey.getTaskDoingKey(type));
    }

    private Map<String, String> getDoingTaskMap(String type) {
        return redisService.getHashALL(CacheKey.getTaskDoingKey(type));
    }

    private Map<String, String> getDoneTaskMap(String type) {
        return redisService.getHashALL(CacheKey.getTaskDoneKey(type));
    }

    public boolean isRunning(String type) {
        return todoTaskNum(type) > 0;
    }

    /**
     * 从左边加任务
     */
    public void addTask(TaskInfo taskInfo) {
        redisService.lPush(CacheKey.getTaskTodoKey(taskInfo.getType()), JSON.toJSONString(taskInfo));
    }

    /**
     * 从右边取任务
     */
    public TaskInfo nextTask(String type) {
        String taskJson = redisService.rPorp(CacheKey.getTaskTodoKey(type));
        Long aLong = redisService.lLen(CacheKey.getTaskTodoKey(type));
        log.info("任务开始-获取{} todo队列数据 taskInfo:{}", type, taskJson);
        if (taskJson == null) {
            return null;
        }
        TaskInfo taskInfo = JSON.parseObject(taskJson, TaskInfo.class);
        if (taskInfo != null) {
            redisService.pushHash(CacheKey.getTaskDoingKey(type), taskInfo.toString() , taskJson);
            log.info("任务开始-添加{} Doing Map数据 key:{}", type, taskInfo.toString());
        }
        return taskInfo;
    }

    /**
     * 从右边加回任务
     */
    public void addBackTask(TaskInfo taskInfo) {
        redisService.rPush(CacheKey.getTaskTodoKey(taskInfo.getType()), JSON.toJSONString(taskInfo));
        redisService.delHash(CacheKey.getTaskDoingKey(taskInfo.getType()), taskInfo.toString());
    }

    public void addDone(TaskInfo taskInfo) {
        redisService.delHash(CacheKey.getTaskDoingKey(taskInfo.getType()), taskInfo.toString());
        log.info("任务完成-删除{} Doing Map数据 key:{}",taskInfo.getType() , taskInfo);
        String doneKey = CacheKey.getTaskDoneKey(taskInfo.getBatchNo());
        boolean isFirstTime = !redisService.checkKeyIsExist(doneKey);
        redisService.pushHash(doneKey, taskInfo.toString(), JSON.toJSONString(taskInfo));
        if(isFirstTime) {
            redisService.expireKey(doneKey, Constants.DAYS_30);
        }
        log.info("任务完成-添加{} Done Map数据 batchNo:{}, taskInfo:{}", taskInfo.getType(), taskInfo.getBatchNo(), JSON.toJSONString(taskInfo));
    }

    public Integer refreshDoingTask(String type) {
        AtomicInteger count = new AtomicInteger();
        Map<String, String> map = redisService.getHashALL(CacheKey.getTaskDoingKey(type));
        map.values().forEach(taskJson -> {
            redisService.rPush(CacheKey.getTaskTodoKey(type), taskJson);
            log.info("刷新{} doing map任务数据到todo队列中，taskInfo:{}", type, taskJson);
            count.getAndIncrement();
        });
        log.info("刷新{} doing map任务数据到todo队列中，总量:{}", type, count.get());
        return count.get();
    }

    /**
     * 根据type 获取任务执行进度
     */
    public Map<String, Object> status(String type, String batchNo) {
        Long todoNum = todoTaskNum(type);
        Long doingNum = doingTaskNum(type);
        Map<String, String> doingMap = getDoingTaskMap(type);
        Long doneNum = doneTaskNum(batchNo);
        Map<String, String> doneMap = getDoneTaskMap(batchNo);
        Map<String, Object> result = new HashMap<>();
        result.put("todoNum", todoNum+"");
        result.put("doingNum", doingNum+"");
        result.put("doingMap", doingMap);
        result.put("doneNum:"+batchNo, doneNum+"");
        result.put("doneMap:"+batchNo, doneMap);
        return result;
    }
}
