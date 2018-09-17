package com.ldbc.impls.workloads.ldbc.snb.cypher.converter;

import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class CypherConverter extends Converter {

    final static String DATETIME_FORMAT = "yyyyMMddHHmmssSSS";
    final static String DATE_FORMAT = "yyyyMMdd";

    @Override
    public String convertDateTime(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    private static long convertDateTimesToEpoch(long dateValue, String format) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(Long.toString(dateValue)).toInstant().toEpochMilli();

    }

    /**
     * Converts timestamp strings (in the format produced by DATAGEN) ({@value #DATAGEN_FORMAT})
     * to a date.
     *
     * @param timestamp
     * @return
     */
    public static long convertLongTimestampToEpoch(long timestamp) throws ParseException {
        return convertDateTimesToEpoch(timestamp, DATETIME_FORMAT);
    }

    /**
     * Converts timestamp strings (in the format produced by DATAGEN) ({@value #DATE_FORMAT})
     * to a date.
     *
     * @param date
     * @return
     */
    public static long convertLongDateToEpoch(long date) throws ParseException {
        return convertDateTimesToEpoch(date, DATE_FORMAT);
    }

    public static int convertStartAndEndDateToLatency(long from, long to) throws ParseException {
        long fromEpoch = convertDateTimesToEpoch(from, DATETIME_FORMAT);
        long toEpoch = convertDateTimesToEpoch(to, DATETIME_FORMAT);
        return (int)((toEpoch - fromEpoch) / 1000 / 60);
    }

    public String convertOrganisations(List<LdbcUpdate1AddPerson.Organization> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> "[" + v.organizationId() + ", " + v.year() + "]")
                .collect(Collectors.joining(","));
        res += "]";
        return res;
    }

}
