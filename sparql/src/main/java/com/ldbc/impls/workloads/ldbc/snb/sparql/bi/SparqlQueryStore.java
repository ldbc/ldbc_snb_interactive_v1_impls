package com.ldbc.impls.workloads.ldbc.snb.sparql.bi;

import com.ldbc.impls.workloads.ldbc.snb.IQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.converter.Converter;
import com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlConverter;

public interface SparqlQueryStore extends IQueryStore {

    default Converter getConverter() {
        return new SparqlConverter();
    }

}
