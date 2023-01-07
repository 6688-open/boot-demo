package com.dj.boot.preheat.task;

import com.dj.boot.preheat.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class TaskScannerManager {

    public Map<String, TaskScanner> taskScannerMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        startTaskScanner(Constants.TASK_TYPE_NATIONALITY);
    }

    private void startTaskScanner(String type) {
        if (taskScannerMap.containsKey(type)) {
            return;
        }
        TaskScanner taskScanner = new TaskScanner(type, 50);
        taskScanner.start();
        taskScannerMap.put(type, taskScanner);
    }
}
