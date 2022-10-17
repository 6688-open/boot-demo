package com.dj.boot.controller.excel.listener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单差异对比数据
 *
 * @author wangjia@fescotech.com
 * @date 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillDifferData implements Serializable {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 序列号
     */
    private String serialNo;

    /**
     * 业务年月
     */
    private String busiYm;

    /**
     * 证件类型
     */
    private String certificateType;

    /**
     * 证件号码
     */
    private String certificateNum;

    /**
     * json数据
     */
    private String jsonData;

    /**
     * 用于区分来源文件 1 附件1 2 附件2
     */
    private Integer fileNo;

    /**
     * 用于区分来源文件sheet顺序 相同就是对比的sheet页
     */
    private Integer sheetNo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 数据版本
     */
    private Integer dataVersion;
}