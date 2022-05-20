package org.ldbcouncil.snb.impls.workloads.cypher.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;

public class CypherConverter extends Converter {

    public String convertOrganisations(List<LdbcUpdate1AddPerson.Organization> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> "[" + v.getOrganizationId() + ", " + v.getYear() + "]")
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

}
