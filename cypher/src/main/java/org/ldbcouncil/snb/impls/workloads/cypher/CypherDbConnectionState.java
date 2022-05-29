package org.ldbcouncil.snb.impls.workloads.cypher;

import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;
import org.ldbcouncil.snb.impls.workloads.QueryStore;

import java.io.IOException;
import java.util.Map;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public class CypherDbConnectionState<TDbQueryStore extends QueryStore> extends BaseDbConnectionState<TDbQueryStore>
{
    protected final Driver driver;

    public CypherDbConnectionState( Map<String, String> properties, TDbQueryStore store ) {
        super(properties, store);

        final String endpointURI = properties.get( "endpoint" );
        final String username = properties.get( "user" );
        final String password = properties.get( "password" );

        driver = GraphDatabase.driver( endpointURI, AuthTokens.basic( username, password ) );
    }

    public Session getSession( SessionConfig config )
    {
        return driver.session( config );
    }

    @Override
    public void close() throws IOException
    {
        driver.close();
    }
}
