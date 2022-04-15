package com.dj.boot.aspect.log.output;

import com.alibaba.fastjson.JSON;
import com.dj.boot.aspect.log.log4j.LoggerFactory;
import org.apache.logging.log4j.Logger;

/**
 * 接口日志，用来记录请求的接口数据及返回
 * Date: 2018-11-15
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceLog {

    private static final Logger log = LoggerFactory.getLogger(InterfaceLog.class.getName());
    private static final String FLAG = "\001";
    public static final String DEFAULT = "null";
    private static final String version = "V1.0";
    private static final String LOGFLAG = "interfaceLog"; //多记录发送到统一队列，用于消费程序区分

    /**
     * 日志唯一标示
     */
    private Long id;

    /**
     * 日志打印时间
     */
    private String logDate = DEFAULT;

    /**
     * 线程名称
     */
    private String threadName = DEFAULT;

    /**
     * 业务ID
     */
    private String bizNo = DEFAULT;


    /**
     * 消耗时间(ms)
     */
    private String runTime = DEFAULT;

    /**
     * 接口名称
     */
    private String interfaceName = DEFAULT;

    /**
     * 部署机器ip
     */
    private String serverIp = DEFAULT;


    /**
     * 请求部分
     */
    private String reqInfo = DEFAULT;

    /**
     * 应答
     */
    private String respInfo = DEFAULT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(String reqInfo) {
        this.reqInfo = reqInfo;
    }

    public String getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(String respInfo) {
        this.respInfo = respInfo;
    }

    public void print() {
        final StringBuilder sb = new StringBuilder();
        sb.append(LOGFLAG);
        sb.append(FLAG).append(version);
        sb.append(FLAG).append(bizNo);
        sb.append(FLAG).append(runTime);
        sb.append(FLAG).append(interfaceName);
        sb.append(FLAG).append(serverIp);
        sb.append(FLAG).append(reqInfo);
        sb.append(FLAG).append(respInfo);
        log.info(sb.toString());
    }

    /**
     * 判断是否为本类型日志
     *
     * @param logText log日志文本，日志格式为%d{yyyy-MM-dd HH:mm:ss}\001[%t]\001%m%n
     *                %m为print()输出格式
     * @return
     */
    public static boolean isOwn(String logText) {
        if (logText == null || logText.trim() == "" || logText.trim().length() < 1) {
            return Boolean.FALSE;
        }
        String[] msgs = logText.split(FLAG);
        if (msgs.length < 10) {
            return Boolean.FALSE;
        }
        if (!LOGFLAG.equals(msgs[2])) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public InterfaceLog(String logText) {
        String[] msgs = logText.split(FLAG);
        this.logDate = msgs[0];
        this.threadName = msgs[1];
        this.bizNo = msgs[4];
        this.runTime = msgs[5];
        this.interfaceName = msgs[6];
        this.serverIp = msgs[7];
        this.reqInfo = msgs[8];
        this.respInfo = msgs[9];
    }

    /**
     * 更新属性并打印
     * @param bizNo
     * @param interfaceName
     * @param respInfo
     * @param reqInfo
     */
    public void upAttrAndPrint(String bizNo, String interfaceName, String timeInterval, Object respInfo, Object... reqInfo ) {
        this.runTime = timeInterval;
        this.bizNo = bizNo;
        this.interfaceName = interfaceName;
        try {
            this.reqInfo = JSON.toJSONString(reqInfo);
            this.respInfo = JSON.toJSONString(respInfo);
        } catch (Exception e) {
            this.reqInfo = DEFAULT;
            this.respInfo = DEFAULT;
        }
        print();
    }
    public InterfaceLog() {
    }

}
