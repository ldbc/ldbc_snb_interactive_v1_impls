package org.ldbcouncil.impls.workloads.ldbc.snb.cypher.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.impls.workloads.ldbc.snb.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class CypherConverter extends Converter {

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
