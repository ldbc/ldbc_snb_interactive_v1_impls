package com.ldbc.impls.workloads.ldbc.snb.cypher.interactive;

import com.ldbc.impls.workloads.ldbc.snb.IQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.cypher.converter.CypherConverter;

public interface CypherQueryStore extends IQueryStore {

    default Converter getConverter() {
        return new CypherConverter();
    }

}
