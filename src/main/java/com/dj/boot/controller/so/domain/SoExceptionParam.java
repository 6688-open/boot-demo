package com.dj.boot.controller.so.domain;



import com.dj.boot.btp.common.util.noUtil.BizTypeEnum;
import com.dj.boot.btp.common.util.noUtil.Constant;
import com.dj.boot.btp.common.util.noUtil.OrderNoUtil;
import com.dj.boot.controller.so.constant.SoIssuedErrorEnum;

import java.util.Date;

public class SoExceptionParam {

    /**
     * 异常ID
     */
    private Long id;

    /**
     * 订单主键
     */
    private Long soId;

    /**
     * 订单号
     */
    private String soNo;

    /**
     * 恢复任务ID
     */
    private String renewNo;

    /**
     * 异常类型
     */
    private SoIssuedErrorEnum errType;

    /**
     * 异常发生原因的额外描述
     */
    private String errReason;


    /**
     * 异常恢复原因
     */
    private String renewReason;

    /**
     * 操作人/操作系统
     */
    private String operateUser;

    /**
     * 订单异常需要worker修复
     */
    private SoTask soTask;

    /**
     * 订单异常状态（取消，无法生产，无法分拣，无法发货）
     * 该字段请采用<code>SoErrStatusUtil</code>解析
     * 异常状态1：0:是否取消，1:取消成功/失败
     * 异常状态2：2:是否有异常暂停，3:暂停处理/暂停恢复
     * 异常状态3：4:是否审核，5:审核通过/审核驳回
     */
    private String soErrStatus;

    /**
     * 异常恢复参数
     */
    private SoExceptionResumeParam resumeParam;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 是否为系统自动触发，默认为false
     */
    private boolean autoTask=false;

    public String getRenewNo() {
        return renewNo;
    }

    public void setRenewNo(String renewNo) {
        this.renewNo = renewNo;
    }

    public SoTask getSoTask() {
        return soTask;
    }

    public void setSoTask(SoTask soTask) {
        this.soTask = soTask;
    }

    public Long getSoId() {
        if (soId != null) {
            return soId;
        } else {
            if (soNo != null) {
                return OrderNoUtil.reverseNo2Id(soNo, Constant.BizType.CSL_SERVICE) == -1L ? null : OrderNoUtil.reverseNo2Id(soNo, Constant.BizType.CSL_SERVICE);
            } else {
                return null;
            }
        }
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public String getSoNo() {
        if (soNo != null) {
            return soNo;
        } else {
            if (soId != null) {
                return OrderNoUtil.generateNo(BizTypeEnum.CSL_SERVICE, soId);
            } else {
                return null;
            }
        }
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public SoIssuedErrorEnum getErrType() {
        return errType;
    }

    public void setErrType(SoIssuedErrorEnum errType) {
        this.errType = errType;
    }

    public String getErrReason() {
        return errReason;
    }

    public void setErrReason(String errReason) {
        this.errReason = errReason;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRenewReason() {
        return renewReason;
    }

    public void setRenewReason(String renewReason) {
        this.renewReason = renewReason;
    }

    public String getSoErrStatus() {
        return soErrStatus;
    }

    public void setSoErrStatus(String soErrStatus) {
        this.soErrStatus = soErrStatus;
    }

    public SoExceptionResumeParam getResumeParam() {
        return resumeParam;
    }

    public void setResumeParam(SoExceptionResumeParam resumeParam) {
        this.resumeParam = resumeParam;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public boolean isAutoTask() {
        return autoTask;
    }

    public void setAutoTask(boolean autoTask) {
        this.autoTask = autoTask;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"soId\":")
                .append(soId);
        sb.append(",\"soNo\":\"")
                .append(soNo).append('\"');
        sb.append(",\"renewNo\":\"")
                .append(renewNo).append('\"');
        sb.append(",\"errType\":")
                .append(errType);
        sb.append(",\"errReason\":\"")
                .append(errReason).append('\"');
        sb.append(",\"renewReason\":\"")
                .append(renewReason).append('\"');
        sb.append(",\"operateUser\":\"")
                .append(operateUser).append('\"');
        sb.append(",\"soTask\":")
                .append(soTask);
        sb.append(",\"soErrStatus\":\"")
                .append(soErrStatus).append('\"');
        sb.append(",\"operateTime\":\"")
                .append(operateTime).append('\"');
        sb.append(",\"autoTask\":\"")
                .append(autoTask).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
