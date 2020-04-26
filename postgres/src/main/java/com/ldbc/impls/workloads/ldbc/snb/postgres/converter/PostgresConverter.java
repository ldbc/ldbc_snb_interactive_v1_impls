package com.ldbc.impls.workloads.ldbc.snb.postgres.converter;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PostgresConverter extends Converter {

    final String POSTGRES_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'";
    final DateTimeFormatter dfPostgres = DateTimeFormatter.ofPattern(POSTGRES_DATETIME_FORMAT).withZone(GMT);

    @Override
    public String convertDateTime(Date date) {
        return "'" + dfPostgres.format(date.toInstant()) + "'::timestamp";
    }

    @Override
    public String convertString(String value) {
        return "'" + value.replace("'", "''") + "'";
    }

    public String convertStringList(List<String> values) {
        return "'{" +
                values
                        .stream()
                        .map(v -> "\"" + v + "\"")
                        .collect(Collectors.joining(", ")) +
                "}'::text[]";
    }


    public static Iterable<String> arrayToStringArray(ResultSet r, int column) throws SQLException {
        Array value = r.getArray(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            String[] strs = (String[]) value.getArray();
            List<String> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                array.add(strs[i]);
            }
            return array;
        }
    }

    public static Iterable<List<Object>> arrayToObjectArray(ResultSet r, int column) throws SQLException {
        Array value = r.getArray(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            Object[][] strs = (Object[][]) value.getArray();
            List<List<Object>> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                array.add(new ArrayList(Arrays.asList(strs[i])));
            }
            return array;
        }
    }

    public static Iterable<Long> convertLists(Iterable<List<Object>> arr) {
        List<Long> new_arr = new ArrayList<>();
        List<List<Object>> better_arr = (List<List<Object>>) arr;
        for (List<Object> entry : better_arr) {
            new_arr.add((Long) entry.get(0));
        }
        new_arr.add((Long) better_arr.get(better_arr.size() - 1).get(1));
        return new_arr;
    }

    public static long stringTimestampToEpoch(ResultSet r, int column) throws SQLException {
        return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
    }

}
