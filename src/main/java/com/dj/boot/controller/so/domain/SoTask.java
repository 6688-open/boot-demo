package com.dj.boot.controller.so.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SoTask  {

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单主键
     */
    private Long soId;

    /**
     * 订单异常ID
     */
    private Long soErrId;

    /**
     * 订单异常的key值
     */
    private Integer soErrKey;

    /**
     * 任务内容（存放报文的jfs_key）
     */
    private String content;

    /**
     * 重试次数
     */
    private int retryNumber;

    /**
     * 分库ID
     */
    private Byte gid;


    /**
     * 分片字段
     */
    private Byte regionNo;


    /**
     * 任务类型
     */
    private Byte taskType;

    /**
     * 当前状态（1:待执行,2:成功,3:失败）
     */
    private Byte status;

    /**
     * 开始扫描时间
     */
    private Date startScanTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志
     */
    private byte yn;

    /**
     * 关联的task标识
     */
    private String taskPart;

    /**
     * 商家编号
     */
    private String sellerNo;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 事业部编号
     */
    private String deptNo;

    /**
     * 事业部名称
     */
    private String deptName;

    /**
     * 订单编号
     */
    private String soNo;

    /**
     * 销售平台单号
     */
    private String spSoNo;

    /**
     * 商家Id
     */
    private Long sellerId;

    /**
     * 事业部ID
     */
    private Long deptId;

    /**
     * 异常提醒标识
     */
    private Byte errTipSign;

}
