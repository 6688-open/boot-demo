package com.dj.boot.common.util.unit.test;

import com.dj.boot.common.util.unit.LengthUnit;
import com.dj.boot.common.util.unit.VolumeUnit;
import com.dj.boot.common.util.unit.WeightUnit;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-06-09-17-37
 */
public class UnitTest {

    @Test
    public void test () {

        //厘米 转 毫米
        BigDecimal bigDecimal = LengthUnit.fromUnitLiteral("CM").toMillimeter(new BigDecimal(1000));
        //米 转 毫米
        BigDecimal bigDecimal1 = LengthUnit.METER.toMillimeter(new BigDecimal("1000"));
        BigDecimal m = LengthUnit.fromUnitLiteral("M").toMillimeter(new BigDecimal("1000"));
        BigDecimal cm = LengthUnit.fromUnitLiteral("CM").convert(new BigDecimal("1000"), LengthUnit.METER);

        //立方厘米 转 立方毫米
        BigDecimal cm1 = VolumeUnit.fromUnitLiteral("CM").toCubeMillimeter(new BigDecimal(1000));
        //立方分米 转 立方厘米
        BigDecimal bigDecimal2 = VolumeUnit.CUBE_DECIMETER.toCubeCentimeter(new BigDecimal("1000"));
        BigDecimal dm = VolumeUnit.fromUnitLiteral("DM").toCubeCentimeter(new BigDecimal("1000"));
        BigDecimal cm2 = VolumeUnit.fromUnitLiteral("CM").convert(new BigDecimal("1000"), VolumeUnit.CUBE_DECIMETER);

        //克 转 千克
        BigDecimal g = WeightUnit.fromUnitLiteral("G").toKilogram(new BigDecimal(1000));
        //千克 转 克
        BigDecimal bigDecimal3 = WeightUnit.KILOGRAM.toGram(new BigDecimal("1000"));
        BigDecimal kg = WeightUnit.fromUnitLiteral("KG").toGram(new BigDecimal("1000"));
        BigDecimal g1 = WeightUnit.fromUnitLiteral("G").convert(new BigDecimal("1000"), WeightUnit.KILOGRAM);

    }
}
