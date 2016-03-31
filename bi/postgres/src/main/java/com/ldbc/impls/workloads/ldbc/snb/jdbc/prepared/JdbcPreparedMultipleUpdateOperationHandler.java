package com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;

public abstract class JdbcPreparedMultipleUpdateOperationHandler<OperationType extends Operation<LdbcNoResult>, OperationResult, QueryStore> 
implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Connection conn = state.getConnection();
	try {
		List<PreparedStatement> stmts = getStatements(conn, state, operation);
		for (PreparedStatement stmt : stmts) {
			state.logQuery(operation.getClass().getSimpleName(), stmt.toString());
			stmt.execute();
			stmt.close();
		}
	} catch (SQLException e) {
		System.out.println(e);
		throw new RuntimeException(e);
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

public abstract List<PreparedStatement> getStatements(Connection conn, JdbcDbConnectionStore<QueryStore> state, OperationType operation);
}