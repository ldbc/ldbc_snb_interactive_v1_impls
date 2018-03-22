package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPosts;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery2PersonPostsResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 2
 * Given a start Person, retrieve the last 10 Messages (Posts or Comments) created by that person.
 * For each message, return that message, the original post in its conversation, and the author of that post.
 * Order results descending by message creation date, then descending by message identifier
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery2Handler implements OperationHandler<LdbcShortQuery2PersonPosts,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery2Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery2PersonPosts operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.personId());

        String query = "g.V().has('Person.id',$id)" +
                ".in('hasCreator')" +
                ".order()" +
                ".by('creationDate',decr)" +
                ".limit(10)" +
                ".match(__.as('x').valueMap().as('message'), " +
                       "__.as('x').optional(until(hasLabel('Post')).repeat(out('replyOf'))).valueMap().as('replied')," +
                       "__.as('x').out('hasCreator').valueMap('Person.id','firstName','lastName').as('creator'))"+
                ".select('message','replied','creator')\n";

        try {
            ResultSet resultSet = dbConnectionState.runQuery(query, parameters);
            ArrayList<LdbcShortQuery2PersonPostsResult> results = new ArrayList<LdbcShortQuery2PersonPostsResult>();
            for (Result r : resultSet) {
                Map<String,List<Object>> map = (Map<String,List<Object>>)r.getObject();
                Map<String,List<Object>> message = (Map<String,List<Object>>)map.get("message");
                Map<String,List<Object>> replied = (Map<String,List<Object>>)map.get("replied");
                Map<String,List<Object>> creator = (Map<String,List<Object>>)map.get("creator");
                Long messageId = 0L;
                if(message.get("Comment.id") != null) {
                    messageId = (Long)message.get("Comment.id").get(0);
                } else {
                    messageId = (Long)message.get("Post.id").get(0);
                }
                LdbcShortQuery2PersonPostsResult ldbcResult = new LdbcShortQuery2PersonPostsResult(
                        messageId,
                        (String)message.get("content").get(0),
                        (Long)message.get("creationDate").get(0),
                        (Long)replied.get("Post.id").get(0),
                        (Long)creator.get("Person.id").get(0),
                        (String)creator.get("firstName").get(0),
                        (String)creator.get("lastName").get(0));
                results.add(ldbcResult);
            }
            resultReporter.report(CODE_OK, results, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}