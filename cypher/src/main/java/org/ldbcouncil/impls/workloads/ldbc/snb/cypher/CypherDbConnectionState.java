package org.ldbcouncil.impls.workloads.ldbc.snb.cypher;

import org.ldbcouncil.snb.driver.DbConnectionState;

import java.io.IOException;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public class CypherDbConnectionState extends DbConnectionState
{

    protected final Driver driver;
    protected final CypherQueryStore queryStore;

    public CypherDbConnectionState( Driver driver, CypherQueryStore queryStore )
    {
        this.driver = driver;
        this.queryStore = queryStore;
    }

    public Session getSession( SessionConfig config )
    {
        return driver.session( config );
    }

    public String getQuery( String operation )
    {
        return queryStore.getQuery( operation );
    }

    public boolean hasQuery( String operation )
    {
        return queryStore.hasQuery( operation );
    }

    public boolean addQuery( String operation )
    {
        return queryStore.addQuery( operation );
    }

    @Override
    public void close() throws IOException
    {

    }
}
