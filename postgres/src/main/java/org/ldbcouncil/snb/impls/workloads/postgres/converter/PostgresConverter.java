package org.ldbcouncil.snb.impls.workloads.postgres.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcQuery1Result;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.sql.Array;
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

public class PostgresConverter extends Converter {

    @Override
    public String convertDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "timestamp with time zone '" + sdf.format(date) + "'";
    }

    public String convertDate(Date date) {
        return super.convertDate(date) + "::date";
    }

    public static OffsetDateTime convertDateToOffsetDateTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"));
        return zdt.toOffsetDateTime();
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
            return Arrays.asList((String[]) value.getArray());
        }
    }

    public static Iterable<Long> arrayToLongArray(ResultSet r, int column) throws SQLException {
        Array value = r.getArray(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            return Arrays.asList((Long[]) value.getArray());
        }
    }

    public static Iterable<LdbcQuery1Result.Organization> arrayToOrganizationArray(ResultSet r, int column) throws SQLException {
        Array value = r.getArray(column);
        if (value == null) {
            return new ArrayList<>();
        } else {
            return Arrays.asList((Object[][]) value.getArray()).stream().map(
                    x -> new LdbcQuery1Result.Organization((String) x[0],  Integer.parseInt((String) x[1]), (String) x[2])
            ).collect(Collectors.toList());
        }
    }

    public static long stringTimestampToEpoch(ResultSet r, int column) throws SQLException {
        return r.getTimestamp(column, Calendar.getInstance(TimeZone.getTimeZone("GMT"))).getTime();
    }

}
