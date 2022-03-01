package com.ldbc.impls.workloads.ldbc.snb.postgres.operationhandlers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.ldbc.driver.Operation;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PostgresOperationHandler {

    private String queryStringWithQuestionMarks;
    private Multimap<String, Integer> positions;

    // TODO: cache resulting query/parameter multimap

    /**
     * Replaces parameters with question marks (e.g. ":personId" -> "?").
     *
     * Note that this is done with a simple regex which also performs the replacement in comments.
     * Therefore, queries with parameters in comments are likely to break.
     */
    public String replaceParameterNamesWithQuestionMarks(Operation operation, String queryString, List<String> extraParameterNames) {
        Map<String, Object> parameters = operation.parameterMap();
        String paramMatchingRegex = String.format(":(%s)", parameters.keySet().stream().collect(Collectors.joining("|")));

        Matcher matcher = Pattern.compile(paramMatchingRegex).matcher(queryString);

        positions = HashMultimap.create();
        // JDBC parameters use 1-based indexing
        int pos = 1;
        while (matcher.find()) {
            String param = matcher.group(1);
            positions.get(param).add(pos);
            pos++;
        }

        // change parameters to question mark
        queryStringWithQuestionMarks = queryString.replaceAll(paramMatchingRegex, "?");

        // change extra parameters to question mark
        if (!extraParameterNames.isEmpty()) {
            String extraParameterMatchingRegex = String.format(":(%s)", extraParameterNames.stream().collect(Collectors.joining("|")));
            queryStringWithQuestionMarks = queryStringWithQuestionMarks.replaceAll(extraParameterMatchingRegex, "?");
        }

        return queryStringWithQuestionMarks;
    }

    public String replaceParameterNamesWithQuestionMarks(Operation operation, String queryString) {
        return replaceParameterNamesWithQuestionMarks(operation, queryString, ImmutableList.of());
    }

    public PreparedStatement prepareSnbStatement(Operation operation, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryStringWithQuestionMarks);
        Map<String, Object> parameterMap = operation.parameterMap();
        for (Map.Entry<String, Object> parameter : parameterMap.entrySet()) {
            for (Integer parameterIndex : positions.get(parameter.getKey())) {
                Object value = parameter.getValue();

                if (value instanceof Integer) {
                    stmt.setInt(parameterIndex, (Integer) value);
                } else if (value instanceof Long) {
                    stmt.setLong(parameterIndex, (Long) value);
                } else if (value instanceof String) {
                    stmt.setString(parameterIndex, (String) value);
                } else if (value instanceof Date) {
                    stmt.setObject(parameterIndex, PostgresConverter.convertDateToOffsetDateTime((Date) value));
                } else {
                    throw new RuntimeException("Type not supported: " + value.getClass().getName());
                }
            }
        }
        return stmt;
    }

}
