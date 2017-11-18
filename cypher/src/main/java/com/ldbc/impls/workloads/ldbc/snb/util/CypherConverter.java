package com.ldbc.impls.workloads.ldbc.snb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CypherConverter extends Converter {

    final static String LONG_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * Converts epoch seconds to a date to a long value of format {@value #LONG_FORMAT}?
     *
     * @param timestamp
     * @return
     */
    public String convertDate(long timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(LONG_FORMAT);
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
        final SimpleDateFormat sdf = new SimpleDateFormat(LONG_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(Long.toString(timestamp)).toInstant().toEpochMilli();
    }

}
