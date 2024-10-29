package org.pt.flightbooking.application.utils;

public class NumberUtilsConfig {

    public static double round(final double value) {
        if (Double.isInfinite(value)) return 0.00;
        final long rounded = (Math.round(value * 100));
        return rounded / 100.0;
    }
}
