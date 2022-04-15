package com.dj.boot.pojo.errorreason;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 异常报备原因实体
 */
@Data
@Builder
@TableName("error_reason")
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReason implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 报备名称
     */
    private String reportName;

    /**
     * 报备描述
     */
    private String reportDesc;

    /**
     * 父报备id
     */
    private Long parentCode;

    /**
     * 父报备名称
     */
    private String parentName;

    /**
     * 级别
     */
    private Byte errorLevel;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 逻辑删除
     */
    private Byte isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 异常报备外部编码
     */
    private String reportOuterNo;

}
