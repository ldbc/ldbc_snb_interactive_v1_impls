package com.ldbc.impls.workloads.ldbc.snb.postgres;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;

import java.util.Map;

public class PostgresQueryStore extends QueryStore {

    protected Converter getConverter() {
        return new PostgresConverter();
    }

    @Override
    protected String prepare(QueryType queryType, Map<String, String> parameterSubstitutions) {
        String querySpecification = queries.get(queryType);
        for (String parameter : parameterSubstitutions.keySet()) {
            querySpecification = querySpecification.replace(
                    getParameterPrefix() + parameter + getParameterPostfix(),
                    parameterSubstitutions.get(parameter)
            );
        }
        return querySpecification;
    }

    @Override
    protected String getParameterPrefix() {
        return ":";
    }

    public PostgresQueryStore(String path) throws DbException {
        super(path, ".sql");
    }

}
