/*
 * 这是工具自动生成的类文件
 */
package com.dj.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 事业部主键
     */
    private Long deptId;

    /**
     * 事业部名称
     */
    private String deptName;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺类型
     */
    private byte type;










}
