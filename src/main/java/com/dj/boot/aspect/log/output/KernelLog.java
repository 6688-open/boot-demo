/*
package com.dj.boot.aspect.log.output;

import com.dj.boot.aspect.log.log4j.LoggerFactory;
import org.apache.logging.log4j.Logger;


*/
/**
 * Created with IntelliJ IDEA.
 * Date: 2018/5/4
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 *//*

public class KernelLog {

    private static final Logger log = LoggerFactory.getLogger(KernelLog.class.getName());
    private static final String FLAG = "\001";
    public static final String DEFAULT = "null";

    public enum RunStateEnum {
        SUCCESS("success"),
        ERROR("error"),
        SUSPEND("suspend");

        private String desc;

        RunStateEnum(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum TypeEnum {
        COMPONENT("component"),
        SEQUENCE("sequence"),
        TASK("task");
        private String desc;

        TypeEnum(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }


    */
/**
     * 日志唯一标示
     *//*

    private Long id;

    */
/**
     * 日志打印时间
     *//*

    private String logDate = DEFAULT;

    */
/**
     * 线程名称
     *//*

    private String threadName = DEFAULT;

    */
/**
     * 运行状态
     *//*

    private String runState = DEFAULT;

    */
/**
     * 一次运行的标识
     *//*

    private String session = DEFAULT;


    */
/**
     * 流程ID
     *//*

    private String sequenceId = DEFAULT;

    */
/**
     * 类型
     *//*

    private String type = DEFAULT;

    */
/**
     * 流程业务编码
     *//*

    private String bizNo = DEFAULT;

    */
/**
     * 组件id
     *//*

    private String componentId = DEFAULT;


    */
/**
     * 数据
     *//*

    private String data = DEFAULT;

    */
/**
     * 运行时间(ms)
     *//*

    private String runTime = DEFAULT;

    */
/**
     * 运行机器
     *//*

    private String host = DEFAULT;

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

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void print() {
        final StringBuilder sb = new StringBuilder();
        sb.append(runState);
        sb.append(FLAG).append(session);
        sb.append(FLAG).append(bizNo);
        sb.append(FLAG).append(type);
        sb.append(FLAG).append(sequenceId);
        sb.append(FLAG).append(componentId);
        sb.append(FLAG).append(runTime);
        sb.append(FLAG).append(data);
        log.info(sb.toString());
    }

    */
/**
     * 判断是否为本类型日志，本类型日式是第一个，没有标志字符
     * @param logText log日志文本，日志格式为%d{yyyy-MM-dd HH:mm:ss}\001[%t]\001%m%n
     *                %m为print()输出格式
     * @return
     *//*

    public static boolean isOwn(String logText) {
        if (logText == null || logText.trim() == "" || logText.trim().length() < 1) {
            return Boolean.FALSE;
        }
        String[] msgs = logText.split(FLAG);
        if (msgs.length < 10) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

    */
/**
     * 根据日志内容构造对象
     * @param logText
     *//*

    public KernelLog(String logText) {
        String[] msgs = logText.split(FLAG);
        this.logDate = msgs[0];
        this.threadName = msgs[1];
        this.runState = msgs[2];
        this.session = msgs[3];
        this.bizNo = msgs[4];
        this.type = msgs[5];
        this.sequenceId = msgs[6];
        this.componentId = msgs[7];
        this.runTime = msgs[8];
        this.data = msgs[9];
    }

    public KernelLog() {
    }

}
*/
