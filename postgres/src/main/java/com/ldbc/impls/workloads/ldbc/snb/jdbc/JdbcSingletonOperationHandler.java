package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class JdbcSingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, QueryStore> 
implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

@Override
public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
		ResultReporter resultReporter) throws DbException {
	Connection conn = state.getConnection();
	OperationResult tuple = null;
	int resultCount = 0;
	String queryString = getQueryString(state, operation);
	try {
		Statement stmt = conn.createStatement();
		
		state.logQuery(operation.getClass().getSimpleName(), queryString);

		ResultSet result = stmt.executeQuery(queryString);
		if (result.next()) {
			resultCount++;
			
			tuple = convertSingleResult(result);
			if (state.isPrintResults())
				System.out.println(tuple.toString());
		}
		stmt.close();
		conn.close();
	} catch (SQLException e) {
		throw new DbException(queryString+e);
	} catch (Exception e) {
		throw new DbException(e);
	}
	resultReporter.report(resultCount, tuple, operation);			
}

public abstract String getQueryString(JdbcDbConnectionStore<QueryStore> state, OperationType operation);
public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}