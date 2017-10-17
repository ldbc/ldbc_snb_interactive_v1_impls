package com.ldbc.impls.workloads.ldbc.snb.jdbc;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, QueryStore> 
	implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
			ResultReporter resultReporter) throws DbException {
		Connection conn = state.getConnection();
		List<OperationResult> results = new ArrayList<OperationResult>();
		int resultCount = 0;
		results.clear();
		
		String queryString = getQueryString(state, operation);
		try {
			Statement stmt = conn.createStatement();
			
			state.logQuery(operation.getClass().getSimpleName(), queryString);

			ResultSet result = stmt.executeQuery(queryString);
			while (result.next()) {
				resultCount++;

				OperationResult tuple = convertSingleResult(result);
				if (state.isPrintResults())
					System.out.println(tuple.toString());
				results.add(tuple);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new DbException(queryString+e);
		} catch (Exception e) {
			throw new DbException(e);
		}
		resultReporter.report(resultCount, results, operation);			
	}
	
	public abstract String getQueryString(JdbcDbConnectionStore<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}
