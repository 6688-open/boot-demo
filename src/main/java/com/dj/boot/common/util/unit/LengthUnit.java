package com.dj.boot.common.util.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 长度单位转换工具
 * @author wangjia
 */
public enum LengthUnit {
    METER("m") {
        @Override
        public BigDecimal convert(BigDecimal value, LengthUnit unit) {
            return unit.toMeter(value);
        }

        @Override
        public BigDecimal toMeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toDecimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_10)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCentimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_100)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    DECIMETER("dm") {
        @Override
        public BigDecimal convert(BigDecimal value, LengthUnit unit) {
            return unit.toDecimeter(value);
        }

        @Override
        public BigDecimal toMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_10), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toDecimeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCentimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_10)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_100)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    CENTIMETER("cm") {
        @Override
        public BigDecimal convert(BigDecimal value, LengthUnit unit) {
            return unit.toCentimeter(value);
        }

        @Override
        public BigDecimal toMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_100), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toDecimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_10), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCentimeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_10)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    MILLIMETER("mm") {
        @Override
        public BigDecimal convert(BigDecimal value, LengthUnit unit) {
            return unit.toMillimeter(value);
        }

        @Override
        public BigDecimal toMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toDecimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_100), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCentimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_10), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toMillimeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    };
    public static final int SCALE = 3;
    private static final String _10 = "10";
    private static final String _100 = "100";
    private static final String _1000 = "1000";
    private String unitLiteral;

    LengthUnit(String unitLiteral) {
        this.unitLiteral = unitLiteral;
    }

    public static LengthUnit fromUnitLiteral(String unitLiteral) {
        LengthUnit[] units = LengthUnit.values();
        for (LengthUnit unit : units) {
            if (unit.unitLiteral.equalsIgnoreCase(unitLiteral)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("unknown length unit ["+unitLiteral+"],support[m,dm,cm,mm]");
    }

    public String unitLiteral() {
        return unitLiteral;
    }

    public abstract BigDecimal convert(BigDecimal value, LengthUnit unit);

    public abstract BigDecimal toMeter(BigDecimal value);

    public abstract BigDecimal toDecimeter(BigDecimal value);

    public abstract BigDecimal toCentimeter(BigDecimal value);

    public abstract BigDecimal toMillimeter(BigDecimal value);
}
