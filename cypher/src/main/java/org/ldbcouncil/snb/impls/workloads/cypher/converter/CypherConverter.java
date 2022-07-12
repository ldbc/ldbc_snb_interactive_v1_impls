package org.ldbcouncil.snb.impls.workloads.cypher.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcQuery1Result;
import org.ldbcouncil.snb.driver.workloads.interactive.queries.LdbcInsert1AddPerson;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CypherConverter extends Converter {

    public String convertOrganisations(List<LdbcInsert1AddPerson.Organization> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> "[" + v.getOrganizationId() + ", " + v.getYear() + "]")
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

    public static List<LdbcQuery1Result.Organization> asOrganization(List<List<Object>> value){
        List<LdbcQuery1Result.Organization> orgs = new ArrayList<>();
        for (List<Object> list : value) {
            orgs.add(new LdbcQuery1Result.Organization((String)list.get(0), ((Long) list.get(1)).intValue(), (String)list.get(2)));
        }
        return orgs;
    }
}
