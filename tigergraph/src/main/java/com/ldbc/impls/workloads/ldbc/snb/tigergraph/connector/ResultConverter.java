package com.ldbc.impls.workloads.ldbc.snb.tigergraph.connector;

import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class ResultConverter {
    //    final static String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'";
    final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date parseDateTime(String representation) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(representation);
    }

    public static long dateToEpoch(Date date) {
        return date.toInstant().getEpochSecond();
    }
    public static String dateToEpochString(Date date) {
        return Long.toString(dateToEpoch(date));
    }

    // XXX TODO test new version (below) and if works remove the old code
    public static Iterable<List<Object>> toOrgListNew(List<List> values) {
        return values.stream()
                .map(v -> Arrays.asList(v.get(0), Integer.parseInt((String) v.get(1)), v.get(2)))
                .collect(Collectors.toList());
    }

    // XXX TODO test new version (above) and if works remove the old code
    public static Iterable<List<Object>> toOrgList(List<Object> values) {
        return values.stream()
                .map(v -> {
                            List o = (List) v;
                            return Arrays.asList(
                                    o.get(0), Integer.parseInt((String) o.get(1)), o.get(2)
                            );
                        }
                )
                .collect(Collectors.toList());
    }

    public static String orgsToString(List<LdbcUpdate1AddPerson.Organization> organizations) {
        String newres = organizations
                .stream()
                .map(v -> v.organizationId() + "," + v.year())
                .collect(Collectors.joining(";"));
        return newres;

    }
}
