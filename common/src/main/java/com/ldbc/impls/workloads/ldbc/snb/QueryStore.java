package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.DbException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Superclass of query stores.
 *
 * Note: we deliberately do not use the {@link com.ldbc.driver.Operation#parameterMap()} method, because
 *
 * <ol>
 *   <li>some formats necessitate to handle ids differently from simple longs</li>
 *   <li>we would like to avoid any costs for type checking (e.g. using instanceof for each value)</li>
 * </ol>
 *
 * @param <TQuery>
 */
public abstract class QueryStore<TQuery> implements IQueryStore {

    protected Map<TQuery, String> queries = new HashMap<>();

    protected String loadQueryFromFile(String path, String filename) throws DbException {
        final String filePath = path + File.separator + filename;
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new DbException("Could not load query: " + filePath);
        }
    }

    protected String prepare(TQuery queryType, Map<String, String> parameterSubstitutions) {
        String querySpecification = queries.get(queryType);
        for (String parameter : parameterSubstitutions.keySet()) {
            querySpecification = querySpecification.replace(
                    getParameterPrefix() + parameter + getParameterPostfix(),
                    parameterSubstitutions.get(parameter)
            );
        }
        return querySpecification;
    }

}
