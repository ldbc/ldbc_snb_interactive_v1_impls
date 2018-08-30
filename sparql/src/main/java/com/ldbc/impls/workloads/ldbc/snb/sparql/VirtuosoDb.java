package com.ldbc.impls.workloads.ldbc.snb.sparql;

import com.ldbc.driver.DbException;
import com.ldbc.driver.control.LoggingService;
import com.ldbc.driver.workloads.ldbc.snb.bi.*;
import com.ldbc.driver.workloads.ldbc.snb.interactive.*;
import com.ldbc.impls.workloads.ldbc.snb.db.BaseDb;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlMultipleUpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlSingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.sparql.operationhandlers.SparqlUpdateOperationHandler;
import org.openrdf.query.BindingSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ldbc.impls.workloads.ldbc.snb.sparql.converter.SparqlInputConverter.*;

public abstract class VirtuosoDb extends SparqlDb {

    @Override
    protected void onInit(Map<String, String> properties, LoggingService loggingService) throws DbException {
        dcs = new VirtuosoDbConnectionState(properties, new SparqlQueryStore(properties.get("queryDir")));
    }

    private static String appendInputDefine(SparqlDbConnectionState state, String query){
        return "define input:default-graph-uri <" + VirtuosoDbConnectionState.class.cast(state).getGraphUri() + ">\n" + query;
    }

    public static class Update1AddPerson extends SparqlDb.Update1AddPerson {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate1AddPerson operation) {
            List<String> queries = super.getQueryString(state, operation);
            List<String> newQueries = new ArrayList<String>();
            for(String oldQuery : queries){
                newQueries.add(appendInputDefine(state,oldQuery));
            }
            return newQueries;
        }

    }

    public static class Update2AddPostLike extends SparqlDb.Update2AddPostLike {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate2AddPostLike operation) {
            String query = super.getQueryString(state, operation);
            return appendInputDefine(state, query);
        }

    }

    public static class Update3AddCommentLike extends SparqlDb.Update3AddCommentLike {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate3AddCommentLike operation) {
            String query = super.getQueryString(state, operation);
            return appendInputDefine(state, query);
        }

    }

    public static class Update4AddForum extends SparqlDb.Update4AddForum {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate4AddForum operation) {
            List<String> queries = super.getQueryString(state, operation);
            List<String> newQueries = new ArrayList<String>();
            for(String oldQuery : queries){
                newQueries.add(appendInputDefine(state,oldQuery));
            }
            return newQueries;
        }

    }

    public static class Update5AddForumMembership extends SparqlDb.Update5AddForumMembership {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate5AddForumMembership operation) {
            String query = super.getQueryString(state, operation);
            return appendInputDefine(state, query);
        }

    }

    public static class Update6AddPost extends SparqlDb.Update6AddPost {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate6AddPost operation) {
            List<String> queries = super.getQueryString(state, operation);
            List<String> newQueries = new ArrayList<String>();
            for(String oldQuery : queries){
                newQueries.add(appendInputDefine(state,oldQuery));
            }
            return newQueries;
        }

    }

    public static class Update7AddComment extends SparqlDb.Update7AddComment {

        @Override
        public List<String> getQueryString(SparqlDbConnectionState state, LdbcUpdate7AddComment operation) {
            List<String> queries = super.getQueryString(state, operation);
            List<String> newQueries = new ArrayList<String>();
            for(String oldQuery : queries){
                newQueries.add(appendInputDefine(state,oldQuery));
            }
            return newQueries;
        }

    }

    public static class Update8AddFriendship extends SparqlDb.Update8AddFriendship {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcUpdate8AddFriendship operation) {
            String query = super.getQueryString(state, operation);
            return appendInputDefine(state, query);
        }

    }

    public static class Query7 extends SparqlDb.Query7 {

        @Override
        public String getQueryString(SparqlDbConnectionState state, LdbcQuery7 operation) {
            return appendInputDefine(state, state.getQueryStore().getQuery7WithSecond(operation));
        }

    }
}
