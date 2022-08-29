package org.ldbcouncil.snb.impls.workloads.mssql.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcQuery1Result;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class SQLServerConverter extends Converter {

    @Override
    public String convertDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'" + sdf.format(date) + "'";
    }


    public String convertDate(Date date) {
        return super.convertDate(date) + "";
    }

    @Override
    public String convertString(String value) {
        if (value == null){
            return "''";
        }
        return "'" + value.replace("'", "''") + "'";
    }

    public String convertStringList(List<String> values) {
        return "'{" +
                values
                        .stream()
                        .map(v -> "\"" + v + "\"")
                        .collect(Collectors.joining(", ")) +
                "}'";
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

    public static Iterable<Long> arrayToLongArray(ResultSet r, int column) throws SQLException {
        String value = r.getString(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            String[] strs = value.split(";");
            List<Long> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                array.add(Long.parseLong(strs[i]));
            }
            return array;
        }
    }

    public static Iterable<LdbcQuery1Result.Organization> arrayToOrganizationArray(ResultSet r, int column) throws SQLException {
        String value = r.getString(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            String[] strs = value.split(";");
            List<LdbcQuery1Result.Organization> array = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                String[] s = strs[i].split("\\|");
                // the corresponding results of Interactive Q1 (field 12: universities, field 13: companies)
                // both return <string, int32, string> tuples
                array.add(new LdbcQuery1Result.Organization((String) s[0],Integer.parseInt((String) s[1]), (String) s[2]));
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

    public static OffsetDateTime convertDateToOffsetDateTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"));
        return zdt.toOffsetDateTime();
    }

}
