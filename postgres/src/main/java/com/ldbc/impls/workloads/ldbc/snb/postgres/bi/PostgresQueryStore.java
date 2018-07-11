package com.ldbc.impls.workloads.ldbc.snb.postgres.bi;

import com.ldbc.impls.workloads.ldbc.snb.IQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.postgres.converter.PostgresConverter;

public interface PostgresQueryStore extends IQueryStore {

    default Converter getConverter() {
        return new PostgresConverter();
    }

    @Override
    default String getParameterPrefix() {
        return ":";
    }

}
