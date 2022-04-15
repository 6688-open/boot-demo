package com.dj.boot.common.util.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 体积单位单位转换工具
 * @author wangjia
 */
public enum VolumeUnit {

    CUBE_METER("m") {
        @Override
        public BigDecimal convert(BigDecimal value, VolumeUnit unit) {
            return unit.toCubeMeter(value);
        }

        @Override
        public BigDecimal toCubeMeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeDecimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeCentimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000_000)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000_000_000)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    CUBE_DECIMETER("dm") {
        @Override
        public BigDecimal convert(BigDecimal value, VolumeUnit unit) {
            return unit.toCubeDecimeter(value);
        }

        @Override
        public BigDecimal toCubeMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeDecimeter(BigDecimal value) {
             return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeCentimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000)).setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000_000)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    CUBE_CENTIMETER("cm") {
        @Override
        public BigDecimal convert(BigDecimal value, VolumeUnit unit) {
            return unit.toCubeCentimeter(value);
        }

        @Override
        public BigDecimal toCubeMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000_000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeDecimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeCentimeter(BigDecimal value) {
            return  value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeMillimeter(BigDecimal value) {
            return value.multiply(new BigDecimal(_1000)).setScale(SCALE, RoundingMode.HALF_UP);
        }
    },
    CUBE_MILLIMETER("mm") {
        @Override
        public BigDecimal convert(BigDecimal value, VolumeUnit unit) {
            return unit.toCubeMillimeter(value);
        }

        @Override
        public BigDecimal toCubeMeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000_000_000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeDecimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000_000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeCentimeter(BigDecimal value) {
            return value.divide(new BigDecimal(_1000), SCALE, RoundingMode.HALF_UP);
        }

        @Override
        public BigDecimal toCubeMillimeter(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    };
    public static final int SCALE = 3;
    private static final String _1000 = "1000";
    private static final String _1000_000 = "1000000";
    private static final String _1000_000_000 = "1000000000";
    private String unitLiteral;

    VolumeUnit(String unitLiteral) {
        this.unitLiteral = unitLiteral;
    }

    public static VolumeUnit fromUnitLiteral(String unitLiteral) {
        VolumeUnit[] units = VolumeUnit.values();
        for (VolumeUnit unit : units) {
            if (unit.unitLiteral.equalsIgnoreCase(unitLiteral)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("unknown volume unit ["+unitLiteral+"],support[m,dm,cm,mm]");
    }
    public String unitLiteral() {
        return unitLiteral;
    }
    public abstract BigDecimal convert(BigDecimal value, VolumeUnit unit);

    public abstract BigDecimal toCubeMeter(BigDecimal value);

    public abstract BigDecimal toCubeDecimeter(BigDecimal value);

    public abstract BigDecimal toCubeCentimeter(BigDecimal value);

    public abstract BigDecimal toCubeMillimeter(BigDecimal value);
}
