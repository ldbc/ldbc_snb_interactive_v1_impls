package org.ldbcouncil.snb.impls.workloads.age.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13Result;
import org.ldbcouncil.snb.impls.workloads.age.AgeDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;

/**
 * IC13 degraded handler — Apache AGE does not support shortestPath().
 * Returns -1 (the LDBC spec sentinel for "no path exists") always.
 * Disable this query in validate.properties: LdbcQuery13.enable=false
 */
public class AgeIC13OperationHandler
        implements SingletonOperationHandler<LdbcQuery13Result, LdbcQuery13, AgeDbConnectionState> {

    @Override
    public String getQueryString(AgeDbConnectionState state, LdbcQuery13 operation) {
        return null;
    }

    @Override
    public void executeOperation(LdbcQuery13 operation, AgeDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        resultReporter.report(1, new LdbcQuery13Result(-1), operation);
    }
}
