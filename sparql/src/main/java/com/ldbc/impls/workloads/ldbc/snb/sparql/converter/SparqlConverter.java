package com.ldbc.impls.workloads.ldbc.snb.sparql.converter;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SparqlConverter extends Converter {

    final String SPARQL_DATETIME_QUERY_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    final String SPARQL_DATE_FORMAT = "yyyy-MM-dd";

    final DateTimeFormatter dfQuery = DateTimeFormatter.ofPattern(SPARQL_DATETIME_QUERY_FORMAT).withZone(GMT);
    final DateTimeFormatter dfDate = DateTimeFormatter.ofPattern(SPARQL_DATE_FORMAT).withZone(GMT);

    @Override
    public String convertDateTime(Date date) {
        return "\"" + dfQuery.format(date.toInstant()) + "\"^^xsd:dateTime";
    }

    @Override
    public String convertDate(long timestamp) {
        return "\"" + dfDate.format(Instant.ofEpochMilli(timestamp)) + "\"^^xsd:date";
    }

    public long convertDateToEpoch(String timestamp) {
        return Instant.from(dfDate.parse(timestamp)).toEpochMilli();
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
