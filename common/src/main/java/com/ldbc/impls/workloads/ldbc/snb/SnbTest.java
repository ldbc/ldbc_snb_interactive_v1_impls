package com.ldbc.impls.workloads.ldbc.snb;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.OperationHandlerRunnableContext;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.Workload;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;

import java.util.Map;

public abstract class SnbTest {

    protected final BaseDb db;
    protected final Workload workload;

    protected final int LIMIT = 100;

    public SnbTest(BaseDb db, Workload workload) throws DbException {
        this.db = db;
        this.workload = workload;

        Map<Integer, Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
        db.init(getProperties(), null, mapping);
    }

    protected abstract Map<String, String> getProperties();

    public Object runOperation(BaseDb db, Operation<?> op) throws DbException {
        try {
            OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext(op);
            ResultReporter reporter = new ResultReporter.SimpleResultReporter(null);
            handler.operationHandler().executeOperation(op, handler.dbConnectionState(), reporter);
            handler.cleanup();
            return reporter.result();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void run(BaseDb<QueryStore> db, Operation op) throws DbException {
        runOperation(db, op);
    }

}
