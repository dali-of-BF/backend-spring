package com.fang.enums.time;

/**
 * @author FPH
 */

public enum TimeFormatEnum {
    SIMPLE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    DATE_MINUTE("yyyy-MM-dd HH:mm"),
    MINUTE_TIME("HH:mm"),
    HOUR_MINUTE_TIME("HH:mm:ss");

    private final String format;

    private TimeFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return this.format;
    }
}
