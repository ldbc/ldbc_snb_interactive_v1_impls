package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.bi.BiQueryStore;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SingletonOperationHandler<OperationType extends Operation<OperationResult>, OperationResult, TBiQueryStore extends BiQueryStore>
	implements OperationHandler<OperationType, DriverConnectionStore<TBiQueryStore>> {

	public abstract String getQueryString(DriverConnectionStore<TBiQueryStore> state, OperationType operation);
	public abstract OperationResult convertSingleResult(ResultSet result) throws SQLException;
}