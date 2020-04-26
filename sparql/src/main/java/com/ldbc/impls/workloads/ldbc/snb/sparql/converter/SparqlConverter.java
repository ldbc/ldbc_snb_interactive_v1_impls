package com.ldbc.impls.workloads.ldbc.snb.sparql.converter;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SparqlConverter extends Converter {

    public static final String SPARQL_DATETIME_QUERY_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SPARQL_DATETIME_RETURN_LONG_FORMAT  = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String SPARQL_DATETIME_RETURN_SHORT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SPARQL_DATE_FORMAT = "yyyy-MM-dd";

    ZoneId gmt = ZoneId.of("GMT");

    final DateTimeFormatter sdfQuery = DateTimeFormatter.ofPattern(SPARQL_DATETIME_QUERY_FORMAT).withZone(gmt);
    final DateTimeFormatter sdfDate = DateTimeFormatter.ofPattern(SPARQL_DATE_FORMAT).withZone(gmt);

    @Override
    public String convertDateTime(Date date) {
        return "\"" + sdfQuery.format(date.toInstant()) + "\"^^xsd:dateTime";
    }

    @Override
    public String convertDate(long timestamp) {
        return "\"" + sdfDate.format(Instant.ofEpochMilli(timestamp)) + "\"^^xsd:date";
    }

    public long convertDateToEpoch(String timestamp) {
        return Instant.from(sdfDate.parse(timestamp)).toEpochMilli();
    }

    public String convertString(String value) {
        return "\"" + value + "\"";
    }

    @Override
    public String convertStringList(List<String> values) {
        return values
                .stream()
                .map(v -> "(\"" + v + "\")")
                .collect(Collectors.joining(" "));
    }

    @Override
    public String convertBlacklist(List<String> words) {
        return "\"" +
                words.stream()
                        .map(v -> "(" + v + ")")
                        .collect(Collectors.joining("|")) +
                "\"";
    }

    @Override
    public String convertId(long value) {
        return String.format("\"%020d\"^^xsd:long", value);
    }

    @Override
    public String convertIdForInsertion(long value) {
        return String.format("%020d", value);
    }

}
