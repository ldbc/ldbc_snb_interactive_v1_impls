package com.ldbc.impls.workloads.ldbc.snb.jdbc.prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.impls.workloads.ldbc.snb.jdbc.JdbcDbConnectionStore;

public abstract class JdbcPreparedListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, QueryStore> 
	implements OperationHandler<OperationType, JdbcDbConnectionStore<QueryStore>> {

	@Override
	public void executeOperation(OperationType operation, JdbcDbConnectionStore<QueryStore> state,
			ResultReporter resultReporter) throws DbException {
		Connection conn = state.getConnection();
		List<OperationResult> results = new ArrayList<OperationResult>();
		int resultCount = 0;
		results.clear();
		
		String query="";
		
		try {
			PreparedStatement stmt = getStatement(conn, state, operation);
			query=stmt.toString().replace("Pooled statement wrapping physical statement ", "");
			state.logQuery(operation.getClass().getSimpleName(), query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				resultCount++;
				
				OperationResult tuple = convertSingleResult(result);
				if (state.isPrintResults())
					System.out.println(tuple.toString());
				results.add(tuple);
			}
			result.close();
		} catch (SQLException e) {
			throw new DbException("Type: "+operation.getClass()+ "Query: "+query,e);
		} catch (Exception e) {
			throw new DbException("Type: "+operation.getClass()+ "Query: "+query,e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e);
			}
		}
		resultReporter.report(resultCount, results, operation);			
	}
	
	public abstract PreparedStatement getStatement(Connection conn, JdbcDbConnectionStore<QueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}
