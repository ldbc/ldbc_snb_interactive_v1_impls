package com.ldbc.impls.workloads.ldbc.snb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Converter {

    final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'";

    /**
     * Converts epoch seconds to a date to the same format as produced by DATAGEN
     * and surrounds it in single quotes.
     *
     * @param timestamp
     * @return
     */
    public String convertDate(long timestamp) {
        final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'"+sdf.format(new Date(timestamp))+"'";
    }


    /**
     * Converts timestamp strings (in the format produced by DATAGEN) to a date.
     *
     * @param timestamp
     * @return
     */
    public long convertTimestampToEpoch(String timestamp) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(timestamp).toInstant().toEpochMilli();
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
