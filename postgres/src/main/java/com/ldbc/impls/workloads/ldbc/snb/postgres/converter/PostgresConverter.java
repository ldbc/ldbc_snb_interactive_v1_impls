package com.ldbc.impls.workloads.ldbc.snb.postgres.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PostgresConverter extends Converter {

    @Override
    public String convertDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return "'"+sdf.format(date)+"'::timestamp";
    }

    public String convertString(String value) {
        return "'" + value + "'";
    }

    public String convertStringList(List<String> values) {
        return "'{" +
                values
                .stream()
                .map(v -> "\"" + v + "\"")
                .collect( Collectors.joining( "," ) ) +
                "}'::text[]";
    }

}
