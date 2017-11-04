package com.ldbc.impls.workloads.ldbc.snb.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Converter {

    /**
     * Converts epoch seconds to a date to the same format as produced by DATAGEN
     * and surrounds it in single quotes.
     *
     * @param timestamp
     * @return
     */
    public String convertDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'"+sdf.format(new Date(timestamp))+"'";
    }

    /**
     * Surrounds Strings in single quotes.
     *
     * @param value
     * @return
     */
    public String convertString(String value) {
        return "'" + value + "'";
    }


    /**
     * Convert strings to a comma-separated list between square brackets.
     *
     * @param values
     * @return
     */
    public String convertStringList(List<String> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> "'" + v + "'")
                .collect( Collectors.joining( "," ) );
        res += "]";
        return res;
    }

}
