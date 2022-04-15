package com.dj.boot.test.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品明细对象
 * @Author: wangJia
 * @Date: 2021-08-31-14-09
 */
public class GoodsBsMqDTO implements Serializable {
    /**
     * 编号 商品编码
     */
    private String code;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private String goodsNum;
    /**
     * 商品赔付金额
     */
    private BigDecimal goodsPayMoney;
    /**
     * 物权归属
     */
    private Integer goodOwner;
    /**
     * 物权归属名称
     */
    private String goodOwnerName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getGoodsPayMoney() {
        return goodsPayMoney;
    }

    public void setGoodsPayMoney(BigDecimal goodsPayMoney) {
        this.goodsPayMoney = goodsPayMoney;
    }

    public Integer getGoodOwner() {
        return goodOwner;
    }

    public void setGoodOwner(Integer goodOwner) {
        this.goodOwner = goodOwner;
    }

    public String getGoodOwnerName() {
        return goodOwnerName;
    }

    public void setGoodOwnerName(String goodOwnerName) {
        this.goodOwnerName = goodOwnerName;
    }
}
