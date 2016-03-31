package com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;

public abstract class JdbcPreparedSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore> 
implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Connection conn = state.getConnection();
	OperationResult tuple = null;
	int resultCount = 0;
	String queryString="";
	
	try {
		PreparedStatement stmt = getStatement(conn, state, operation);
		queryString=stmt.toString().replace("Pooled statement wrapping physical statement ", "");
		state.logQuery(operation.getClass().getSimpleName(), queryString);
		ResultSet result = stmt.executeQuery();
		
		if (result.next()) {
			resultCount++;
			
			tuple = convertSingleResult(result);
			if (state.isPrintResults())
				System.out.println(tuple.toString());
		}
		result.close();
	} catch (SQLException e) {
		throw new DbException(queryString+e);
	} catch (Exception e) {
		throw new DbException(e);
	} finally {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}
	resultReporter.report(resultCount, tuple, operation);			
}

public abstract PreparedStatement getStatement(Connection conn, JdbcDbConnectionStore<QueryStore> state, OperationType operation);
public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}