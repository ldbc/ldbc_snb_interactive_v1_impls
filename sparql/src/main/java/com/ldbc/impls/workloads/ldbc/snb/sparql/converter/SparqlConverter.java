package com.ldbc.impls.workloads.ldbc.snb.sparql.converter;

import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class SparqlConverter extends Converter {

    public static final String SPARQL_DATETIME_QUERY_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SPARQL_DATETIME_RETURN_LONG_FORMAT  = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String SPARQL_DATETIME_RETURN_SHORT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SPARQL_DATE_FORMAT = "yyyy-MM-dd";


    final SimpleDateFormat sdfQuery = new SimpleDateFormat(SPARQL_DATETIME_QUERY_FORMAT);
    final SimpleDateFormat sdfReturnLong = new SimpleDateFormat(SPARQL_DATETIME_RETURN_LONG_FORMAT);
    final SimpleDateFormat sdfReturnShort = new SimpleDateFormat(SPARQL_DATETIME_RETURN_SHORT_FORMAT);
    final SimpleDateFormat sdfDate = new SimpleDateFormat(SPARQL_DATE_FORMAT);

    public SparqlConverter() {
        sdfQuery.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdfReturnLong.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdfReturnShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public String convertDateTime(Date date) {
        return "\"" + sdfQuery.format(date) + "\"^^xsd:dateTime";
    }

    @Override
    public String convertDate(long timestamp) {
        return "\"" + sdfDate.format(new Date(timestamp)) + "\"^^xsd:date";
    }

    public long convertDateToEpoch(String timestamp) throws ParseException {
        return sdfDate.parse(timestamp).toInstant().toEpochMilli();
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
