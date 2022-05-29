package org.ldbcouncil.snb.impls.workloads.cypher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery2;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery3;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery4;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery5;
import org.ldbcouncil.snb.driver.workloads.interactive.LdbcQuery9;
import org.ldbcouncil.snb.impls.workloads.QueryStore;
import org.ldbcouncil.snb.impls.workloads.converter.Converter;
import org.ldbcouncil.snb.impls.workloads.cypher.converter.CypherConverter;

public class CypherQueryStore extends QueryStore
{
    private final String path;
    private final String fileSuffix = ".cypher";
    private final Map<String, String> queries = new HashMap<>();

    public CypherQueryStore( String path )  throws DbException
    {
        super(path, ".cypher");
        this.path = path;
    }

    protected Converter getConverter() {
        return new CypherConverter();
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

    static protected Date addDays( Date startDate, int days )
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.DATE, days );
        return cal.getTime();
    }

    static protected Date addMonths( Date startDate, int months )
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.MONTH, months );
        return cal.getTime();
    }


    @Override
    public String getQuery2(LdbcQuery2 operation) {
        return prepare(QueryType.InteractiveComplexQuery2, new ImmutableMap.Builder<String, String>()
        .put(LdbcQuery2.PERSON_ID, getConverter().convertId(operation.getPersonIdQ2() ))
        .put(LdbcQuery2.MAX_DATE, getConverter().convertId(operation.getMaxDate().getTime() ))
        .put( LdbcQuery2.LIMIT, getConverter().convertInteger(operation.getLimit() ))
        .build());
    }

    @Override
    public String getQuery3(LdbcQuery3 operation) {
        final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
        return prepare(QueryType.InteractiveComplexQuery3, new ImmutableMap.Builder<String, String>()
        .put( LdbcQuery3.PERSON_ID, getConverter().convertId(operation.getPersonIdQ3() ))
        .put( LdbcQuery3.COUNTRY_X_NAME, getConverter().convertString(operation.getCountryXName() ))
        .put( LdbcQuery3.COUNTRY_Y_NAME, getConverter().convertString(operation.getCountryYName()) )
        .put( LdbcQuery3.START_DATE, getConverter().convertId(operation.getStartDate().getTime() ))
        .put( "endDate", getConverter().convertId(endDate.getTime() ))
        .put( LdbcQuery3.LIMIT, getConverter().convertInteger(operation.getLimit() ))
        .build());
    }

    @Override
    public String getQuery4(LdbcQuery4 operation) {
        final Date endDate = addDays( operation.getStartDate(), operation.getDurationDays() );
        return prepare(QueryType.InteractiveComplexQuery4, new ImmutableMap.Builder<String, String>()
        .put(LdbcQuery4.PERSON_ID, getConverter().convertId(operation.getPersonIdQ4()))
        .put(LdbcQuery4.START_DATE, getConverter().convertLong(operation.getStartDate().getTime()))
        .put( "endDate", getConverter().convertLong(endDate.getTime() ))
        .put( LdbcQuery4.LIMIT, getConverter().convertInteger(operation.getLimit() ))
        .build());
    }

    @Override
    public String getQuery5(LdbcQuery5 operation) {
        return prepare(QueryType.InteractiveComplexQuery5, new ImmutableMap.Builder<String, String>()
        .put(LdbcQuery5.PERSON_ID, getConverter().convertId(operation.getPersonIdQ5()))
        .put(LdbcQuery5.MIN_DATE, getConverter().convertLong(operation.getMinDate().getTime() ))
        .put( LdbcQuery5.LIMIT, getConverter().convertInteger(operation.getLimit() ))
        .build());
    }

    @Override
    public String getQuery9(LdbcQuery9 operation) {
        return prepare(QueryType.InteractiveComplexQuery9, new ImmutableMap.Builder<String, String>()
        .put(LdbcQuery9.PERSON_ID, getConverter().convertId(operation.getPersonIdQ9()))
        .put(LdbcQuery9.MAX_DATE, getConverter().convertLong(operation.getMaxDate().getTime()))
        .put( LdbcQuery9.LIMIT, getConverter().convertInteger(operation.getLimit() ))
        .build());
    }
}
