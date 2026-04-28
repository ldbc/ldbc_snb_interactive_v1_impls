package org.ldbcouncil.snb.impls.workloads.age.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery14Result;
import org.ldbcouncil.snb.impls.workloads.age.AgeDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.ListOperationHandler;

import java.util.Collections;
import java.util.List;

/**
 * IC14 degraded handler — Apache AGE does not support allShortestPaths().
 * Returns an empty list always.
 * Disable this query in validate.properties: LdbcQuery14.enable=false
 */
public class AgeIC14OperationHandler
        implements ListOperationHandler<LdbcQuery14Result, LdbcQuery14, AgeDbConnectionState> {

    @Override
    public String getQueryString(AgeDbConnectionState state, LdbcQuery14 operation) {
        return null;
    }

    @Override
    public void executeOperation(LdbcQuery14 operation, AgeDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        resultReporter.report(0, Collections.<LdbcQuery14Result>emptyList(), operation);
    }
}
