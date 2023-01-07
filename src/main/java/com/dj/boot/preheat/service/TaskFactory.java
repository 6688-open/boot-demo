package com.dj.boot.preheat.service;

import com.dj.boot.preheat.common.Constants;
import com.dj.boot.preheat.domain.TaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
public class TaskFactory {

    private static final TaskFactory instance = new TaskFactory();

    public static TaskFactory getInstance() {
        return instance;
    }

    public List<TaskInfo> generateTaskList(String type) {
        if (Constants.TASK_TYPE_NATIONALITY.equals(type)) {
            return generateUserTaskList(type, "1804_", 1, "dj_user_", 5);
        }
        return new ArrayList<>();
    }

    private List<TaskInfo> generateUserTaskList(String type, String dbPrefix, int dbNum, String tablePrefix, int tableNum) {
        String batchNo = generateBatchNo(type);
        log.info("####### 创建任务批次 type:{}, batchNo:{}, dbNum:{}, tableNum:{}", type, batchNo, dbNum, tableNum);
        List<TaskInfo> list = new ArrayList<>();
        for (int i = tableNum - 1; i >= 0; i--) {
            for (int j = 1; j <= dbNum; j++) {
                TaskInfo ti = new TaskInfo();
                ti.setBatchNo(batchNo);
                ti.setType(type);
                ti.setDb(dbPrefix + j);
                ti.setTable(tablePrefix + getFormatTableIndex("", i));
                list.add(ti);
            }

        }
        return list;
    }

    private String generateBatchNo(String type) {

        String todayDate = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        Random random = new Random();

        return type + "-" + todayDate + "-" + random.nextInt(9);
    }

    private static String getFormatTableIndex(String style, long tbIndex) {
        String tableIndex = null;
        DecimalFormat df = new DecimalFormat();
        if (StringUtils.isEmpty(style)) {
            style = "_000";//在格式后添加诸如单位等字符
        }
        df.applyPattern(style);
        tableIndex = df.format(tbIndex);
        return tableIndex;
    }
}
