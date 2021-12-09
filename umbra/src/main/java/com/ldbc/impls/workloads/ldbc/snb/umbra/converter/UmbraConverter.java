package com.ldbc.impls.workloads.ldbc.snb.umbra.converter;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class UmbraConverter extends Converter {

    @Override
    public String convertDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "timestamp with time zone '" + sdf.format(date) + "'";
    }

    public String convertDate(Date date) {
        return super.convertDate(date) + "::date";
    }

    @Override
    public String convertString(String value) {
        return "'" + value.replace("'", "''") + "'";
    }

    public String convertStringList(List<String> values) {
        return "'[" +
                values
                        .stream()
                        .map(v -> "\"" + v + "\"")
                        .collect(Collectors.joining(", ")) +
                "]'::text[]";
    }


    public static Iterable<String> arrayToStringArray(ResultSet r, int column) throws SQLException {
        String value = r.getString(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            String[] strs = value.split(";");
            List<String> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                array.add(strs[i]);
            }
            return array;
        }
    }

    public static Iterable<List<Object>> arrayToObjectArray(ResultSet r, int column) throws SQLException {
        String value = r.getString(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            String[] strs = value.split(";");
            List<List<Object>> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                String[] s = strs[i].split("\\|");
                // the corresponding results of Interactive Q1 (field 12: universities, field 13: companies)
                // both return <string, int32, string> tuples
                array.add(Arrays.asList(s[0], Integer.valueOf(s[1]), s[2]));
            }
            return array;
        }
    }

    public static Iterable<Long> pathToList(ResultSet r, int column) throws SQLException {
        String value = r.getString(column);
        String[] strs = value.split(";");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            list.add(Long.valueOf(strs[i]));
        }
        return list;
    }

    public static long stringTimestampToEpoch(ResultSet r, int column) throws SQLException {
        return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
    }


    public static long timestampToEpoch(ResultSet r, int column) throws SQLException {
        return r.getTimestamp(column).getTime();
    }

    public static long dateToEpoch(ResultSet r, int column) throws SQLException {
        return r.getDate(column).getTime();
    }

}
