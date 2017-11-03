package com.ldbc.impls.workloads.ldbc.snb.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateConverter {

    /**
     * Converts epoch seconds to PostgreSQL timestamps.
     * @param timestamp
     * @return
     */
    public static String convertDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'"+sdf.format(new Date(timestamp))+"'::timestamp";
    }

    /**
     * Converts epoch seconds to a date to the same format as produced by DATAGEN.
     * @param timestamp
     * @return
     */
    public static String convertDateDatagenFormat(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'"+sdf.format(new Date(timestamp))+"'";
    }
}
