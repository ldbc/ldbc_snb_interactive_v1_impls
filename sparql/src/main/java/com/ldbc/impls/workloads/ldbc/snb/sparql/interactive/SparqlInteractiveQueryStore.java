package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveQueryStore;
import com.ldbc.impls.workloads.ldbc.snb.sparql.bi.SparqlQueryStore;

public class SparqlInteractiveQueryStore extends InteractiveQueryStore implements SparqlQueryStore  {

    public SparqlInteractiveQueryStore(String path) throws DbException {
        super(path, ".sparql");
    }

}
