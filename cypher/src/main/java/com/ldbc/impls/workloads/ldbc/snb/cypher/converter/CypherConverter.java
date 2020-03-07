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

    final static String DATETIME_FORMAT = "yyyy-MM-dd"; //  HH:mm:ss.SSS?
    final static String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public String convertDateTime(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "datetime('" + sdf.format(date) + "')";
    }

    @Override
    public String convertDate(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "datetime('" + sdf.format(date) + "')";
    }

    private static long convertDateTimesToEpoch(long dateValue, String format) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(Long.toString(dateValue)).toInstant().toEpochMilli();

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
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

}
