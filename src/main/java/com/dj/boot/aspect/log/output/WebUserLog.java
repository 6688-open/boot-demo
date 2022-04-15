/*
package com.dj.boot.aspect.log.output;

import com.dj.boot.aspect.log.log4j.LoggerFactory;
import org.apache.logging.log4j.Logger;


*/
/**
 * web应用用户访问日志.
 * Date: 2018/5/4
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 *//*

@Deprecated
public class WebUserLog {

    private static final Logger log = LoggerFactory.getLogger(WebUserLog.class.getName());
    private static final String FLAG = "\001";
    public static final String DEFAULT = "null";
    private static final String version = "V1.0";
    private static final String LOGFLAG = "webUser"; //多记录发送到统一队列，用于消费程序区分

    public enum systemNameEnum {
        SELLER("seller"),
        ADMIN("admin"),
        PARTNER("partner"),
        MTN("mtn");

        private String desc;

        systemNameEnum(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum AccountTypeEnum {
        ERP("1"),
        PASSPORT("2");
        private String desc;

        AccountTypeEnum(String desc) {
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
     * 用户请求session
     *//*

    private String session = DEFAULT;

    */
/**
     * 消耗时间(ms)
     *//*

    private String runTime = DEFAULT;

    */
/**
     * 系统名称
     *//*

    private String systemName = DEFAULT;

    */
/**
     * 接口名称
     *//*

    private String interfaceName = DEFAULT;


    */
/**
     * 同一应用唯一ID
     *//*

    private String uuid = DEFAULT;

    */
/**
     * 操作时间（北京时间），数值型；必填；秒；
     *//*

    private String time = DEFAULT;

    */
/**
     * 部署机器ip
     *//*

    private String serverIp = DEFAULT;

    */
/**
     * 客户端ip
     *//*

    private String clientIp = DEFAULT;


    */
/**
     * 账号类型；必填；
     * 1：erp
     * 2：passport
     *//*

    private String accountType = DEFAULT;

    */
/**
     * 账号名称
     *//*

    private String accountName = DEFAULT;

    */
/**
     * 请求类型（post，get ....）
     *//*

    private String askType = DEFAULT;

    */
/**
     * 请求地址
     *//*

    private String path = DEFAULT;

    */
/**
     * 请求部分
     *//*

    private String reqInfo = DEFAULT;

    */
/**
     * 应答
     *//*

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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getAskType() {
        return askType;
    }

    public void setAskType(String askType) {
        this.askType = askType;
    }

    public void print() {
        final StringBuilder sb = new StringBuilder();
        sb.append(LOGFLAG);
        sb.append(FLAG).append(version);
        sb.append(FLAG).append(session);
        sb.append(FLAG).append(runTime);
        sb.append(FLAG).append(systemName);
        sb.append(FLAG).append(interfaceName);
        sb.append(FLAG).append(uuid);
        sb.append(FLAG).append(time);
        sb.append(FLAG).append(serverIp);
        sb.append(FLAG).append(clientIp);
        sb.append(FLAG).append(accountType);
        sb.append(FLAG).append(accountName);
        sb.append(FLAG).append(askType);
        sb.append(FLAG).append(path);
        sb.append(FLAG).append(reqInfo);
        sb.append(FLAG).append(respInfo);
        log.info(sb.toString());
    }

    */
/**
     * 判断是否为本类型日志
     * @param logText log日志文本，日志格式为%d{yyyy-MM-dd HH:mm:ss}\001[%t]\001%m%n
     *                %m为print()输出格式
     * @return
     *//*

    public static boolean isOwn(String logText) {
        if (logText == null || logText.trim() == "" || logText.trim().length() < 1) {
            return Boolean.FALSE;
        }
        String[] msgs = logText.split(FLAG);
        if (msgs.length < 18) {
            return Boolean.FALSE;
        }
        if(!LOGFLAG.equals(msgs[2])){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public WebUserLog(String logText) {
        String[] msgs = logText.split(FLAG);
        this.logDate = msgs[0];
        this.threadName = msgs[1];
        this.session = msgs[4];
        this.runTime = msgs[5];
        this.systemName = msgs[6];
        this.interfaceName = msgs[7];
        this.uuid = msgs[8];
        this.time = msgs[9];
        this.serverIp = msgs[10];
        this.clientIp = msgs[11];
        this.accountType = msgs[12];
        this.accountName = msgs[13];
        this.askType = msgs[14];
        this.path = msgs[15];
        this.reqInfo = msgs[16];
        this.respInfo = msgs[17];
    }

    public WebUserLog() {
    }

}
*/
