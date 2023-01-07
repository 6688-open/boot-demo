package com.dj.boot.preheat.service;

import com.alibaba.fastjson.JSON;
import com.dj.boot.preheat.common.Constants;
import com.dj.boot.preheat.domain.TaskInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("taskGenerateService")
public class TaskGenerateServiceImpl implements TaskGenerateService{

    @Resource
    private TaskQueueCacheService taskQueueCacheService;

    /**
     * 根据类型创建任务
     */
    public String createTask(String type) {
        Map<String, String> result = new HashMap<>();
        if (taskQueueCacheService.isRunning(type)) {
            result.put(Constants.CODE, "11111");
            result.put(Constants.MSG, "task already running");
        } else {
            String batchNo = "";
            List<TaskInfo> taskInfoList = TaskFactory.getInstance().generateTaskList(type);
            taskInfoList.forEach(ti -> {
                taskQueueCacheService.addTask(ti);
            });
            if (taskInfoList.size()>0) {
                batchNo = taskInfoList.get(0).getBatchNo();
            }

            result.put(Constants.BATCH_NO, batchNo);
            result.put(Constants.CODE, "00000");
            result.put(Constants.MSG, "success");

        }

        return JSON.toJSONString(result);
    }

    /**
     * 将doing队列数据刷新至todo队列重新执行
     */
    public String refreshDoingTask(String type) {
        Integer count = taskQueueCacheService.refreshDoingTask(type);
        return "success refresh num:"+count;
    }

    /**
     * 获取全部任务状态
     */
    public String taskStatus(String type, String batchNo) {
        return JSON.toJSONString(taskQueueCacheService.status(type, batchNo));
    }

}
