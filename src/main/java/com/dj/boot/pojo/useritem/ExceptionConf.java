package com.dj.boot.pojo.useritem;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *异常中心配置
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionConf implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 单据类型
     */
    private String billType;
    /**
     * 异常类型
     */
    private String exceptionType;
    /**
     * 异常描述
     */
    private String exceptionDesc;
    /**
     * 环节
     */
    private String step;
    /**
     * 异常接口
     */
    private String exceptionInterface;

    /**
     *异常外部code
     */
    private String exceptionOutCode;
    /**
     * 异常内部code
     */
    private String exceptionInnerCode;
    /**
     * 异常原因（规范描述）
     */
    private String exceptionReason;
    /**
     * 处理方案
     */
    private String handlerAction;

    /**
     * 处理方案编码
     */
    private String handlerCode;

    /**
     * 处理方
     */
    private Integer handlerGroup;
    /**
     * 数据来源
     *
     */
    private Integer exceptionSource;


    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 删除标志
     */
    private Integer yn;


}
