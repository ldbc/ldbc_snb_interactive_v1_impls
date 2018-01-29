package com.ldbc.impls.workloads.ldbc.snb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CypherConverter extends Converter {

    final static String DATETIME_FORMAT = "yyyyMMddHHmmssSSS";
    final static String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts epoch seconds to a date to a long value of format {@value #DATETIME_FORMAT}?
     *
     * @param timestamp
     * @return
     */
    @Override
    public String convertDateTime(long timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new java.util.Date(timestamp));
    }

    @Override
    public String convertDate(long timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new java.util.Date(timestamp));
    }

    /**
     * Converts timestamp strings (in the format produced by DATAGEN) ({@value #DATAGEN_FORMAT})
     * to a date.
     *
     * @param timestamp
     * @return
     */
    public long convertLongTimestampToEpoch(long timestamp) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(Long.toString(timestamp)).toInstant().toEpochMilli();
    }

}
