package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlUpdateOperationHandler;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.*;

public abstract class StardogDb extends SparqlDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new StardogDbConnectionState(properties, new SparqlQueryStore(properties.get("queryDir")));
    }
}
