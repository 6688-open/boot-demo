package com.dj.boot.feedback.domain;

import com.dj.boot.feedback.constant.FeedbackInterfaceNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: demo
 * @Author: wangJia
 * @Date: 2022-02-16-16-33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackData implements Serializable{

    /**
     * 商家编码
     */
    private String sellerNo;
    /**
     * 货主编码
     */
    private String deptNo;

    /**
     * 业务类型
     * @see FeedbackInterfaceNameEnum
     */
    private Integer bizType;

    /**
     * 业务单号
     */
    private String bizNo;
    /**
     * 消息实体(json)
     */
    private String bodyMsg;

    /**
     * 授权码
     */
    private String pin;

    /**
     * 目标系统编码 1.回传网关 2.qi men 3:zi ying
     */
    private String targetSystemSign;

    /**
     * 执行优先级
     */
    private Integer sequenceNumber;

    /**
     * 回传方式：1-通过网关；2-jmq
     * 如果未空，默认走网关
     */
    private Integer feedbackMode;
    /**
     * 回传方式为mq时，topic值
     */
    private String topic;
}
