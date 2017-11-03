package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ListOperationHandler<OperationType extends Operation<List<OperationResult>>, OperationResult, TQueryStore extends BiQueryStore>
	implements OperationHandler<OperationType, DriverConnectionStore<TQueryStore>> {
	
	public abstract String getQueryString(DriverConnectionStore<TQueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}
