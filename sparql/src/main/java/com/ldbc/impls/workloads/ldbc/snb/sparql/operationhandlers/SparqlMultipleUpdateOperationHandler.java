package com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.MultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.SparqlDbConnectionState;
import org.openrdf.query.Update;
import org.openrdf.repository.RepositoryConnection;

import java.util.List;

public abstract class SparqlMultipleUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements MultipleUpdateOperationHandler<TOperation, SparqlDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, SparqlDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try(final RepositoryConnection conn = state.getRepository().getConnection()){
            List<String> queryStrings = getQueryString(state, operation);
            for (String queryString : queryStrings) {
                state.logQuery(operation.getClass().getSimpleName(), queryString);
                Update update = conn.prepareUpdate(queryString);
                update.execute();
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }

}
