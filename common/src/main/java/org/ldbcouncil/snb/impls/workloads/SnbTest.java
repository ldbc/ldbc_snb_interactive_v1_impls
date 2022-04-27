package org.ldbcouncil.snb.impls.workloads;

import org.ldbcouncil.snb.driver.Db;
import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.Operation;
import org.ldbcouncil.snb.driver.OperationHandlerRunnableContext;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.Workload;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Map;

public abstract class SnbTest<D extends Db>
{

    protected final D db;
    protected final Workload workload;

    protected final int LIMIT = 100;

    public SnbTest( D db, Workload workload )
    {
        this.db = db;
        this.workload = workload;
    }

    @Before
    public void init() throws DbException
    {
        Map<Integer,Class<? extends Operation>> mapping = workload.operationTypeToClassMapping();
        db.init( getProperties(), null, mapping );
    }

    @After
    public void cleanup() throws IOException
    {
        db.close();
        workload.close();
    }

    protected abstract Map<String, String> getProperties();

    public Object runOperation( D db, Operation<?> op ) throws DbException
    {
        OperationHandlerRunnableContext handler = db.getOperationHandlerRunnableContext( op );
        ResultReporter reporter = new ResultReporter.SimpleResultReporter( null );
        handler.operationHandler().executeOperation( op, handler.dbConnectionState(), reporter );
        handler.cleanup();
        return reporter.result();
    }

    protected void run( D db, Operation op ) throws DbException
    {
        runOperation( db, op );
    }
}
