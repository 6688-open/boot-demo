package com.dj.boot.preheat.task.nineelements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 数据处理
 */
@Service
public class NineElementsDataHandle {

    private static final Log logger = LogFactory.getLog(NineElementsDataHandle.class);

    //@Value("${serverRunInfoMap}")
    private String serverRunInfoMapStr="{\"127.0.0.1\":{\"startIndex\":\"1\",\"endIndex\":\"1000\"},\"127.0.0.1\":{\"startIndex\":\"6\",\"endIndex\":\"6\"}}";

    private Map<String, JSONObject> serverRunInfoMap;

    private static String LOCAL_IP;//当前服务器IP
    //启动配置全局初始值
    private int startIndex = 0;
    //启动配置初始值
    private int endIndex = 1000;
    //获取数据每次查询条数

    private int pageSize = 200;

    //@Value("${Data.clean.pageNum}")
    private int pageNum = 1;
    //获取数据同组参数最大重试次数
    private int defoultRetryTimes = 3;
    //成功数
    private int successCount = 0;

    //成功数
    private int levelCheckFailCount = 0;


    static {
        try {
            // 获取本地IP
            //LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
            LOCAL_IP = "127.0.0.1";
        } catch (Exception e) {
            logger.error("[NineElementsDataHandle static error], e");
            LOCAL_IP = "127.0.0.1";
        }
    }

    @PostConstruct
    public void init() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                execute();
            }
        });
        t.start();
    }

    public void execute() {
        try {

            //解析配置
            serverRunInfoMap = JSON.parseObject(serverRunInfoMapStr, Map.class);

            /*this.startIndex = getStartIndex();
            this.endIndex = getEndIndex();
            logger.info("NineElementsDataHandle.execute start execute--LOCAL_IP:" + LOCAL_IP + ", startIndex:" + startIndex + ", endIndex:" + endIndex + "");

            if (startIndex > endIndex) {
                throw new Exception("NineElementsDataHandle.execute 初始配置-开始和结束的索引位置不合法!");
            }*/
            int i = 0;
            int endId = calculationEndId(startIndex);
            List<String> list = query(startIndex, endId, i);


            while (CollectionUtils.isNotEmpty(list)) {


                /*if (list == null || list.size() == 0) {
                    throw new Exception(" error! startIndex:" + startIndex + ", endId:" + endId + "查询无数据，结束获取数据!");
                }*/
                logger.info("NineElementsDataHandle.execute query  data-api success! pageNum:" + pageNum + ", size:" + list.size());

                //处理数据
                handle(list);

                //记录下次开始点
                //startIndex = endId;

                i++;
                pageNum++;
                logger.info("NineElementsDataHandle.execute 本次拉取数据处理成功,下次开始页码:pageNum=" + pageNum);

                list = query(startIndex, endId,i);
            }
            logger.info("NineElementsDataHandle.execute end!!! startIndex: " + getStartIndex());
            logger.info("NineElementsDataHandle.execute end!!! endIndex: " + endIndex);
            logger.info("NineElementsDataHandle.execute end!!! successCount: " + successCount);
            logger.info("NineElementsDataHandle.execute end!!! levelCheckFailCount: " + levelCheckFailCount);
        } catch (Exception e) {
            logger.error("NineElementsDataHandle.execute Exception, startIndex: " + startIndex);
            logger.error("NineElementsDataHandle.execute Exception, endIndex: " + endIndex);
            logger.error("NineElementsDataHandle.execute Exception, successCount: " + successCount);
            logger.error("NineElementsDataHandle.execute Exception, levelCheckFailCount: " + levelCheckFailCount);
            logger.error("NineElementsDataHandle.execute Exception, errorMessage: " + e.getMessage());
        }
    }

    //计算结束的位置
    private int calculationEndId(int startIndex) {
        int endId = startIndex + pageSize;
        return endId > endIndex ? endIndex + 1 : endId;
    }

    /**
     * 根据开始 结束索引位置查询API数据 --同一组参数查寻失败次数 查询超时时间
     *
     * @param idStart
     * @param idEnd
     * @return
     */
    private List<String> query(int idStart, int idEnd, int i) {
        List<String> query = null;
        if (i == 4) {
            return null;
        } else if (i < 4){

            query = Lists.newArrayList();
            query.add(i+"");
            return query;
        }

        Random r = new Random();
        int retryTimes = 0;


        while (retryTimes < defoultRetryTimes) {
            try {
                if (retryTimes != 0) {
                    Thread.sleep(1000L, r.nextInt(500));
                }

                query = Lists.newArrayList();
                query.add("111");
                query.add("222");
                query.add("333");
            } catch (Exception e) {
                logger.error("NineElementsDataHandle.query 查询API数据 error" + e.getMessage());
            }
            if (query != null) {
                return query;
            }
            retryTimes++;

        }
        return query;
    }

    private void handle(List<String> customerIds) throws Exception {
        int currentIndex = startIndex;
        for (String customerId : customerIds) {

            try {
                accountLevelProcess(customerId);
                ++successCount;//记录本批次成功数
                logger.info(" NineElementsDataHandle.handle success - customerId:" + customerId + ",successCount:" + successCount);
                ++currentIndex;//记录全局索引
            } catch (Exception e) {
                logger.error("NineElementsDataHandle.handle error " + e.getMessage());
                throw new Exception("NineElementsDataHandle.handle error currentIndex:" + currentIndex + ",customerId:" + customerId + ",successCount:" + successCount);
            }
        }
    }


    private void accountLevelProcess(String customerId) throws Exception {

    }

    private int getStartIndex() {
        if (serverRunInfoMap != null && serverRunInfoMap.get(LOCAL_IP) != null) {
            return Integer.parseInt(serverRunInfoMap.get(LOCAL_IP).getString("startIndex"));
        }
        return 0;
    }

    private int getEndIndex() {
        if (serverRunInfoMap != null && serverRunInfoMap.get(LOCAL_IP) != null) {
            return Integer.parseInt(serverRunInfoMap.get(LOCAL_IP).getString("endIndex"));
        }
        return 0;
    }

}
