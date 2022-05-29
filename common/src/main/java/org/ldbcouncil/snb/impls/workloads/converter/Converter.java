package org.ldbcouncil.snb.impls.workloads.converter;

import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Converter {

    final static String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'";
    final static String DATE_FORMAT = "yyyy-MM-dd";

    public String convertDateTime(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT+0"));
        return "'" + sdf.format(date) + "'";
    }

    public String convertDate(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT+0"));
        return "'" + sdf.format(date) + "'";
    }

    /**
     * Surrounds a string in single quotes and escape single quotes in the string itself.
     *
     * @param value
     * @return
     */
    public String convertString(String value) {
        return "'" + value.replace("'", "\\'") + "'";
    }

    public String convertInteger(int value) {
        return Integer.toString(value);
    }

    /**
     * Convert strings to a comma-separated list between square brackets.
     *
     * @param values
     * @return
     */
    public String convertStringList(List<String> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> "'" + v + "'")
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

    /**
     * Convert a list of longs to a comma-separated list between square brackets.
     *
     * @param values
     * @return
     */
    public String convertLongList(List<Long> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> v.toString())
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

    /**
     * Convert a list of longs to a comma-separated list between square brackets.
     *
     * @param values
     * @return
     */
    public String convertOrganisations(List<LdbcUpdate1AddPerson.Organization> values) {
        String res = "[";
        res += values
                .stream()
                .map(v -> v.toString())
                .collect(Collectors.joining(", "));
        res += "]";
        return res;
    }

    /**
     * Some implementations, e.g. the (deprecated) SPARQL one, will not work with a simple toString and require some tinkering,
     * e.g. padding the id with '0' characters.
     *
     * @param value
     * @return
     */
    public String convertId(long value) {
        return Long.toString(value);
    }

    /**
     * Some implementations, e.g. the (deprecated) SPARQL one, will not work with a simple toString and require some tinkering,
     * e.g. padding the id with '0' characters.
     *
     * @param value
     * @return
     */
    public String convertLong(long value) {
        return Long.toString(value);
    }


    /**
     * Some implementation, e.g. the (deprecated) SPARQL one, require a different id for updates:
     * while SparqlConverter#convertId() wraps the value with `"00000..."^^xsd:long`,
     * updates require plain `00000...` format.
     *
     * @param value
     * @return
     */
    public String convertIdForInsertion(long value) {
        return convertId(value);
    }

}
