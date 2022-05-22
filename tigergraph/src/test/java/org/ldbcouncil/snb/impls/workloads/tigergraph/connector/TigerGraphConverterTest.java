package org.ldbcouncil.snb.impls.workloads.tigergraph.connector;

import com.google.common.collect.ImmutableList;

import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery1Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import junit.framework.TestCase;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TigerGraphConverterTest extends TestCase {

    public void testParseDateTime() throws ParseException {

        HashMap<String, ZonedDateTime> conversions = new HashMap<>();
        conversions.put("2014-01-01 00:00:00", ZonedDateTime.of(2014, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")));
        conversions.put("1976-02-05 00:00:00", ZonedDateTime.of(1976, 2, 5, 0, 0, 0, 0, ZoneId.of("UTC")));
        conversions.put("2020-12-31 13:12:11", ZonedDateTime.of(2020, 12, 31, 13, 12, 11, 0, ZoneId.of("UTC")));

        conversions.forEach((key, expected) -> {
            Date result = null;
            try {
                result = TigerGraphConverter.parseDateTime(key);
                assertEquals(result.toInstant(), expected.toInstant());
            } catch (ParseException e) {
                e.printStackTrace();
                fail("ParseException thrown");
            }
        });

    }

    public void testToOrgList() {
        List<List> comps1 = Arrays.asList(
                Arrays.asList("5142L", "2004", "dada"),
                Arrays.asList("aaaaa", "2001", "papa")
        );
        Iterable<LdbcQuery1Result.Organization> universities1 = TigerGraphConverter.toOrgList(comps1);
    }

    public void testOrgsToString() {
        List<LdbcUpdate1AddPerson.Organization> orgs = ImmutableList.of(
                new LdbcUpdate1AddPerson.Organization(112233L, 2004),
                new LdbcUpdate1AddPerson.Organization(223344L, 2014)
        );
        String result = TigerGraphConverter.orgsToString(orgs);
        assertEquals(result, "112233,2004;223344,2014");
    }
}