package com.dj.boot.pojo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 销售平台分页查询条件类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryViewCondition implements Serializable {


    /**
     * id
     */
    private String tenantId;

    /**
     * 分类等级
     */
    private Byte level;
    /**
     * 主键
     */
    private String categoryNo;
    /**
     * 名称
     */
    private String categoryName;

    /**
     * 状态：0启用1停用
     */
    private Byte status;

    /**
     * 最高级的treeCode
     */
    private String ppTreeCode;
    /**
     * 外部编码
     */
    private String outCategoryNo;

}
