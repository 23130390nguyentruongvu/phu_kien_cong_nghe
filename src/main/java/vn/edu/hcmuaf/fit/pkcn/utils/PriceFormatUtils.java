package vn.edu.hcmuaf.fit.pkcn.utils;

import java.text.DecimalFormat;

public abstract class PriceFormatUtils {
    public static final String PATTERN_D = "#,### đ";
    public static final String PATTERN_VND = "#,### VND";
    public static final String PATTERN_NONE_UNIT = "#,###";
    private static DecimalFormat df = new DecimalFormat();

    public static String formatPrice(String pattern, Number price) {
        if (pattern == null || pattern.isEmpty()) return "";
        if (price == null) price = 0;
        df.applyPattern(pattern);
        return df.format(price);
    }
}
