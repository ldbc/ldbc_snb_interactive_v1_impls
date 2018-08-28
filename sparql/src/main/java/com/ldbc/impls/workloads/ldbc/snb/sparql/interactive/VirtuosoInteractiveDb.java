package com.ldbc.impls.workloads.ldbc.snb.sparql.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.VirtuosoDb;

import java.util.Map;

public class VirtuosoInteractiveDb extends VirtuosoDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        super.onInit(properties, loggingService);
        SparqlInteractiveDbInitializer.registerQueries(this);
    }

}

