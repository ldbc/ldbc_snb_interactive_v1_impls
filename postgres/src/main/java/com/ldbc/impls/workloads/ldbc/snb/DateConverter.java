package com.ldbc.impls.workloads.ldbc.snb;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateConverter {

    public static String convertDate(long timestamp) {
        //return "to_timestamp("+timestamp+")::timestamp";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return "'"+sdf.format(new Date(timestamp))+"'::timestamp";
    }

}
