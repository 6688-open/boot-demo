package com.dj.boot.common.util.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 重量单位转换工具
 * @author wangjia
 */
public enum WeightUnit {
    GRAM("g") {
        @Override
        public BigDecimal convert(BigDecimal value, WeightUnit unit) {
            return unit.toGram(value);
        }

        @Override
        public BigDecimal toGram(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toKilogram(BigDecimal value) {
            return value.divide(new BigDecimal(_1000), SCALE, RoundingMode.HALF_UP);
        }
    },
    KILOGRAM("kg") {
        @Override
        public BigDecimal convert(BigDecimal value, WeightUnit unit) {
            return unit.toKilogram(value);
        }

        @Override
        public BigDecimal toGram(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toKilogram(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    };
    public static final int SCALE = 3;
    private static final String _1000 = "1000";
    private String unitLiteral;

    WeightUnit(String unitLiteral) {
        this.unitLiteral = unitLiteral;
    }

    /**
     * 从单位字面值解析出单位枚举
     *
     * @param unitLiteral
     * @return
     */
    public static WeightUnit fromUnitLiteral(String unitLiteral) {
        WeightUnit[] units = WeightUnit.values();
        for (WeightUnit unit : units) {
            if (unit.unitLiteral.equalsIgnoreCase(unitLiteral)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("unknown weight unit ["+unitLiteral+"],support[g,kg]");
    }
    public String unitLiteral() {
        return unitLiteral;
    }
    public abstract BigDecimal convert(BigDecimal value, WeightUnit unit);

    public abstract BigDecimal toGram(BigDecimal value);

    public abstract BigDecimal toKilogram(BigDecimal value);
}
