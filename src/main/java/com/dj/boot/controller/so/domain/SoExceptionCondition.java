package com.dj.boot.controller.so.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 订单异常暂停记录
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SoExceptionCondition{


    /**
     * 主键
     */
    private String id;

    /**
     * 订单主键
     */
    private String soId;

    /**
     * 异常类型
     */
    private String errType;
    private String eType;

    /**
     * 暂停状态（异常，恢复）
     */
    private String errStatus;

    /**
     * 暂停状态（异常，恢复）
     */
    private String errStatusDesc;

    /**
     * 暂停原因
     */
    private String errReason;

    /**
     * 异常处理时长
     */
    private String errDuration;

    /**
     * 异常恢复时间
     */
    private String renewTime;

    /**
     * 异常恢复原因
     */
    private String renewReason;

    /**
     * 操作时间
     */
    private String operateTime;

    /**
     * 操作人
     */
    private String operateUser;

    /**
     * 异常操作按钮
     */
    private String processBtn;

    /**
     * 订单编号
     */
    private String soNo;

    /**
     * 暂停时间
     */
    private String pauseTime;

}
