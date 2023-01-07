package com.dj.boot.preheat.task;


import com.dj.boot.preheat.domain.TaskInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class AbstractTaskRunner implements TaskRunner{

    private TaskInfo taskInfo;

    public AbstractTaskRunner(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public void run() {
        try {
            process(taskInfo);
        } catch (Exception e) {
            log.error("处理任务异常！", e);
        }
    }

    protected abstract void process(TaskInfo taskInfo);


}
