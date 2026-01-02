package vn.edu.hcmuaf.fit.pkcn.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public abstract class FormatUtils {
    public static final String PATTERN_VND = "#,### VND";
    private static DecimalFormat df = new DecimalFormat();

    public static String formatPrice(String pattern, BigDecimal price) {
        if (pattern == null || pattern.isEmpty()) return "";
        df.applyPattern(pattern);
        return df.format(price);
    }
}
