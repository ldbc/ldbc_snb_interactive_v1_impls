package com.ldbc.impls.workloads.ldbc.snb.tigergraph;

import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.driver.DbException;

public class TygrysekQueryStore extends QueryStore{

//        public Converter getConverter() {
//            return new TygrysekConverter();
//        }

        public TygrysekQueryStore(String path) throws DbException {
            super(path, ".gsql");
        }

}
