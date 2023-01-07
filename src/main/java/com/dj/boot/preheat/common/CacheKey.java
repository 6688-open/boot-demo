package com.dj.boot.preheat.common;


public class CacheKey {
    public static final String TASK_ODO_QUEUE = "todo_queue";
    public static final String TASK_DOING_MAP = "doing_map";
    public static final String TASK_DONE_MAP = "done_map";

    public static String getTaskTodoKey(String type) {
        return CacheKey.TASK_ODO_QUEUE + "_"+type;
    }

    public static String getTaskDoingKey(String type) {
        return CacheKey.TASK_DOING_MAP + "_"+type;
    }

    public static String getTaskDoneKey(String batchNo) {
        return CacheKey.TASK_DONE_MAP + "_"+batchNo;
    }
}
