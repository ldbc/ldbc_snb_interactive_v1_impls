package org.ldbcouncil.snb.impls.workloads.age;

import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery1Result;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcUpdate1AddPerson;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AgeConverter handles type conversions between Java types and Apache AGE agtype.
 *
 * Write direction (Java -> Cypher literal strings for query substitution):
 *   convertId, convertLong, convertString, convertDate, convertDateTime,
 *   convertLongList, convertOrganisations
 *
 * Read direction (agtype PGobject value strings -> Java types):
 *   toLong, toStr, toBoolean, toStringList, asOrganizationList
 */
public class AgeConverter extends Converter {

    // -------------------------------------------------------------------------
    // Write direction: Java -> Cypher literal (used in prepare() substitution)
    // -------------------------------------------------------------------------

    @Override
    public String convertId(long value) {
        return Long.toString(value);
    }

    @Override
    public String convertLong(long value) {
        return Long.toString(value);
    }

    /** Epoch milliseconds as a plain long literal — AGE stores dates as bigint epoch ms. */
    @Override
    public String convertDate(Date date) {
        return Long.toString(date.getTime());
    }

    @Override
    public String convertDateTime(Date date) {
        return Long.toString(date.getTime());
    }

    /** Quote and escape a string value for embedding in a Cypher literal. */
    @Override
    public String convertString(String value) {
        if (value == null) return "null";
        return "'" + value.replace("\\", "\\\\").replace("'", "\\'") + "'";
    }

    /** Cypher array literal from a list of longs: [1,2,3] */
    @Override
    public String convertLongList(List<Long> values) {
        if (values == null || values.isEmpty()) return "[]";
        return "[" + values.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
    }

    /** Cypher array literal from a list of strings: ['a','b'] */
    @Override
    public String convertStringList(List<String> values) {
        if (values == null || values.isEmpty()) return "[]";
        return "[" + values.stream()
                .map(v -> "'" + v.replace("\\", "\\\\").replace("'", "\\'") + "'")
                .collect(Collectors.joining(",")) + "]";
    }

    /**
     * Cypher array-of-objects literal for UNWIND in IU1 (studyAt / workAt).
     * Produces: [{organizationId:1,year:2010},{organizationId:2,year:2012}]
     */
    @Override
    public String convertOrganisations(List<LdbcUpdate1AddPerson.Organization> values) {
        if (values == null || values.isEmpty()) return "[]";
        return "[" + values.stream()
                .map(v -> "{organizationId:" + v.getOrganizationId() + ",year:" + v.getYear() + "}")
                .collect(Collectors.joining(",")) + "]";
    }

    /** Convenience: convert a list of tag IDs to a Cypher array literal. */
    public static String convertTagIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return "[]";
        return "[" + tagIds.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
    }

    // -------------------------------------------------------------------------
    // Read direction: agtype PGobject.getValue() string -> Java types
    // -------------------------------------------------------------------------

    /**
     * Parse an agtype integer/numeric value to long.
     * agtype numeric columns come back as plain number strings, e.g. "42" or "4398046511333".
     */
    public static long toLong(Object obj) {
        if (obj == null) return -1L;
        String v = obj.toString().trim();
        if ("null".equals(v)) return -1L;
        // Strip agtype type annotations like ::numeric if present
        int ann = v.lastIndexOf("::");
        if (ann >= 0) v = v.substring(0, ann).trim();
        // Strip surrounding quotes if somehow it's a quoted integer
        if (v.startsWith("\"") && v.endsWith("\"")) {
            v = v.substring(1, v.length() - 1);
        }
        return Long.parseLong(v);
    }

    /**
     * Parse an agtype string value.
     * agtype strings come back with surrounding double quotes, e.g. "\"Alice\"".
     */
    public static String toStr(Object obj) {
        if (obj == null) return null;
        String v = obj.toString().trim();
        if ("null".equals(v)) return null;
        if (v.startsWith("\"") && v.endsWith("\"")) {
            return v.substring(1, v.length() - 1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\")
                    .replace("\\/", "/")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\t", "\t");
        }
        return v;
    }

    /**
     * Parse an agtype boolean value.
     */
    public static boolean toBoolean(Object obj) {
        if (obj == null) return false;
        String v = obj.toString().trim();
        return "true".equalsIgnoreCase(v);
    }

    /**
     * Parse an agtype array of strings into a Java List<String>.
     * Input format: ["Alice","Bob"] or [] or null
     */
    public static List<String> toStringList(Object obj) {
        if (obj == null) return Collections.emptyList();
        String v = obj.toString().trim();
        if ("null".equals(v) || "[]".equals(v) || v.isEmpty()) return Collections.emptyList();
        // Strip outer [ ]
        if (v.startsWith("[") && v.endsWith("]")) {
            v = v.substring(1, v.length() - 1).trim();
        }
        if (v.isEmpty()) return Collections.emptyList();
        return parseStringArray(v);
    }

    /**
     * Parse an agtype array of arrays into a List<LdbcQuery1Result.Organization>.
     * Input format: [["MIT",2010,"Cambridge"],["Harvard",2008,"Cambridge"]]
     * Nulls inside outer array are skipped.
     */
    public static List<LdbcQuery1Result.Organization> asOrganizationList(Object obj) {
        if (obj == null) return Collections.emptyList();
        String v = obj.toString().trim();
        if ("null".equals(v) || "[]".equals(v) || v.isEmpty()) return Collections.emptyList();
        List<LdbcQuery1Result.Organization> result = new ArrayList<>();
        // Each element is a sub-array [name, year, city]
        List<String> elements = parseTopLevelArrayElements(v);
        for (String elem : elements) {
            elem = elem.trim();
            if ("null".equals(elem)) continue;
            if (elem.startsWith("[") && elem.endsWith("]")) {
                elem = elem.substring(1, elem.length() - 1).trim();
                List<String> parts = parseStringArray(elem);
                if (parts.size() >= 3) {
                    String name = parts.get(0);
                    int year;
                    try { year = Integer.parseInt(parts.get(1).trim()); } catch (NumberFormatException e) { year = 0; }
                    String city = parts.get(2);
                    result.add(new LdbcQuery1Result.Organization(name, year, city));
                }
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Private parsing helpers
    // -------------------------------------------------------------------------

    /** Split a comma-separated list of values, respecting nested brackets and quotes. */
    private static List<String> parseTopLevelArrayElements(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.isEmpty()) return result;
        // Strip outer brackets if present
        String content = s;
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1);
        }
        int depth = 0;
        boolean inQuote = false;
        int start = 0;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '"' && (i == 0 || content.charAt(i - 1) != '\\')) {
                inQuote = !inQuote;
            } else if (!inQuote) {
                if (c == '[' || c == '{') depth++;
                else if (c == ']' || c == '}') depth--;
                else if (c == ',' && depth == 0) {
                    result.add(content.substring(start, i).trim());
                    start = i + 1;
                }
            }
        }
        if (start < content.length()) {
            result.add(content.substring(start).trim());
        }
        return result;
    }

    /** Parse comma-separated quoted or unquoted string elements. */
    private static List<String> parseStringArray(String content) {
        List<String> result = new ArrayList<>();
        List<String> tokens = parseTopLevelArrayElements(content);
        for (String token : tokens) {
            token = token.trim();
            if (token.startsWith("\"") && token.endsWith("\"")) {
                result.add(token.substring(1, token.length() - 1)
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\"));
            } else {
                result.add(token);
            }
        }
        return result;
    }
}
