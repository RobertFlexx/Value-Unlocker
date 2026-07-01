package com.valueunlocker.util;

import java.math.BigDecimal;

public final class UnlockedNumbers {
    private UnlockedNumbers() {
    }

    public static int toBackendInt(double value) {
        if (Double.isNaN(value)) {
            return 0;
        }
        if (value >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    public static int toThresholdInt(double value) {
        if (Double.isNaN(value)) {
            return 0;
        }
        if (value >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) Math.ceil(value);
    }

    public static int toCommandResult(double value) {
        if (Double.isNaN(value)) {
            return 0;
        }
        if (value >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value <= 0.0D) {
            return 0;
        }
        return Math.max(1, (int) Math.min(Integer.MAX_VALUE, Math.floor(value)));
    }

    public static String format(double value) {
        if (!Double.isFinite(value)) {
            return Double.toString(value);
        }
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }
}
