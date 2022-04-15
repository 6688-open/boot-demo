package com.dj.boot.test.sort_soItem;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * saf对外接口入参
 * 销售订单明细
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SoItemParam {
    private static final long serialVersionUID = 1L;
    /**
     * 主商品ID
     */
    private Long goodsId;

    /**
     * 主商品编号
     */
    private String goodsNo;

    /**
     * 主商品名称
     */
    private String goodsName;

    /**
     * 下单数量
     */
    private BigDecimal applyOutstoreQty;

    /**
     * 商品单价
     */
    private Double price;

    /**
     * 行号
     */
    private String orderLine;

    /**
     *  优先级 数值越小优先级越高
     */
    private Integer priority;





}