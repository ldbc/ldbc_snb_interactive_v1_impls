package org.ldbcouncil.snb.impls.workloads.cypher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CypherQueryStore
{
    private final String path;
    private final String fileSuffix = ".cypher";
    private final Map<String, String> queries = new HashMap<>();

    public CypherQueryStore( String path )
    {
        this.path = path;
    }

    public boolean addQuery( String operation )
    {
        final String query = loadQueryFromFile( operation );
        if ( query != null )
        {
            queries.put( operation, query );
        }
        return query != null;
    }

    public String getQuery( String operation )
    {
        return queries.get( operation );
    }

    public boolean hasQuery( String operation )
    {
        return queries.containsKey( operation );
    }

    private String loadQueryFromFile( String filename )
    {
        final String filePath = path + File.separator + filename + fileSuffix;
        try
        {
            return new String( Files.readAllBytes( Paths.get( filePath ) ) );
        }
        catch ( IOException e )
        {
            System.err.printf( "Unable to load query from file: %s", filePath );
        }
        return null;
    }
}
