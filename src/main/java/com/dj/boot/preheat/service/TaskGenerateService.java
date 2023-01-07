package com.dj.boot.preheat.service;

public interface TaskGenerateService {
    String createTask(String type);
    String refreshDoingTask(String type);
    String taskStatus(String type, String batchNo);
}
