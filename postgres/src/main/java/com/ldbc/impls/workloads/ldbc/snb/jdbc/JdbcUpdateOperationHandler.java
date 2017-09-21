package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;

public abstract class JdbcUpdateOperationHandler<OperationType extends Operation<LdbcNoResult>, OperationResult, QueryStore> 
implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Connection conn = state.getConnection();
	String queryString = getQueryString(state, operation);
	try {
		Statement stmt = conn.createStatement();
		
		state.logQuery(operation.getClass().getSimpleName(), queryString);

		stmt.execute(queryString);
		stmt.close();
	} catch (SQLException e) {
		throw new RuntimeException(queryString+e);
	} catch (Exception e) {
		throw new RuntimeException(e);
	} finally {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
	}
}

public abstract String getQueryString(JdbcDbConnectionStore<QueryStore> state, OperationType operation);
}