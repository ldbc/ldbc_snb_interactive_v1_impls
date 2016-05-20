package com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;

public abstract class JdbcPreparedUpdateOperationHandler<OperationType extends Operation<LdbcNoResult>, OperationResult, QueryStore> 
implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Connection conn = state.getConnection();
	String queryString = "";
	try {
		PreparedStatement stmt = getStatement(conn, state, operation);
		queryString=stmt.toString().replace("Pooled statement wrapping physical statement ", "");
		state.logQuery(operation.getClass().getSimpleName(), queryString);
		if(stmt.execute()) {
			stmt.getResultSet().close();
		} else {
			stmt.getUpdateCount();
		}
	} catch (SQLException e) {
		throw new RuntimeException(queryString+e);
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		try {
			state.freeConnection(conn);
		} catch (DbException e) {
			throw new RuntimeException(e);
		}
		resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
	}
}

public abstract PreparedStatement getStatement(Connection conn, JdbcDbConnectionStore<QueryStore> state, OperationType operation);
}