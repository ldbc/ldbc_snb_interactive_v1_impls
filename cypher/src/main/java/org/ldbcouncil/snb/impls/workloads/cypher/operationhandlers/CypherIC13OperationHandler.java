package org.ldbcouncil.snb.impls.workloads.cypher.operationhandlers;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.ResultReporter;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery13Result;
import org.ldbcouncil.snb.impls.workloads.cypher.CypherDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.operationhandlers.SingletonOperationHandler;

import java.text.ParseException;

import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

public abstract class CypherIC13OperationHandler
        implements SingletonOperationHandler<LdbcQuery13Result,LdbcQuery13,CypherDbConnectionState>
{

    public abstract LdbcQuery13Result toResult( Record record ) throws ParseException;

    @Override
    public void executeOperation( LdbcQuery13 operation, CypherDbConnectionState state,
                                  ResultReporter resultReporter ) throws DbException
    {
        String query = getQueryString(state, operation);


        final SessionConfig config = SessionConfig.builder().withDefaultAccessMode( AccessMode.READ ).build();
        try ( final Session session = state.getSession( config ) )
        {
            final Result result = session.run( query );
            if ( result.hasNext() )
            {
                try
                {
                    resultReporter.report( 1, toResult( result.next() ), operation );
                    final ResultSummary summary = result.consume();
                }
                catch ( ParseException e )
                {
                    throw new DbException( e );
                }
            }
            else
            {
                resultReporter.report( 1, new LdbcQuery13Result( -1 ), operation );
            }
        }
    }
}
