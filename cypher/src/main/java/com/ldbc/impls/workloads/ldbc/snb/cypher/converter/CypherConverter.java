package com.ldbc.impls.workloads.ldbc.snb.cypher.converter;

import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CypherConverter extends Converter {

    final String DATETIME_FORMAT = "yyyy-MM-dd"; //  HH:mm:ss.SSS?
    final String DATE_FORMAT = "yyyy-MM-dd";

    final DateTimeFormatter dfCypherDateTime = DateTimeFormatter.ofPattern(DATETIME_FORMAT).withZone(GMT);
    final DateTimeFormatter dfCypherDate = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(GMT);

    @Override
    public String convertDateTime(Date date) {
        return "datetime('" + dfCypherDateTime.format(date.toInstant()) + "')";
    }

    @Override
    public String convertDate(Date date) {
        return "datetime('" + dfCypherDate.format(date.toInstant()) + "')";
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
