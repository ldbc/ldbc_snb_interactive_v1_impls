package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageReplies;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery7MessageRepliesResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 7
 Given a Message (Post or Comment), retrieve the (1-hop) Comments that reply to it.
 In addition, return a boolean flag indicating if the author of the reply knows the author of the original message.
 If author is same as original author, return false for "knows" flag.
 Order results descending by comment identifier, then descending by author identifier.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery7Handler implements OperationHandler<LdbcShortQuery7MessageReplies,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery7Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery7MessageReplies operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.messageId());

        String queryPost = "g.V().has('Post.id',$id)" +
                ".match( __.as('x').in('replyOf').as('y').valueMap().as('reply'), " +
                        "__.as('y').out('hasCreator').valueMap().as('replyCreator'))" +
                ".select('reply','replyCreator')\n";

        String queryComment = "g.V().has('Comment.id',$id)" +
                ".match( __.as('x').in('replyOf').as('y').valueMap().as('reply'), " +
                "__.as('y').out('hasCreator').valueMap().as('replyCreator'))" +
                ".select('reply','replyCreator')\n";


        try {
            ArrayList<LdbcShortQuery7MessageRepliesResult> results =
                    new ArrayList<LdbcShortQuery7MessageRepliesResult>();
            ResultSet resultSet = dbConnectionState.runQuery(queryPost, parameters);
            Map<String,List<Object>> map = null;
            for (Result r : resultSet) {
                map = (Map<String,List<Object>>)r.getObject();
                Map<String,List<Object>> reply = (Map<String,List<Object>>)map.get("reply");
                Map<String,List<Object>> replyCreator = (Map<String,List<Object>>)map.get("replyCreator");
                LdbcShortQuery7MessageRepliesResult ldbcResult = new LdbcShortQuery7MessageRepliesResult(
                        (Long)reply.get("Comment.id").get(0),
                        (String)reply.get("content").get(0),
                        (Long)reply.get("creationDate").get(0),
                        (Long)replyCreator.get("Person.id").get(0),
                        (String)replyCreator.get("firstName").get(0),
                        (String)replyCreator.get("lastName").get(0),
                        false
                );
                results.add(ldbcResult);
            }
            if (results.size() == 0) {
                resultSet = dbConnectionState.runQuery(queryComment, parameters);
                for (Result r : resultSet) {
                    map = (Map<String,List<Object>>)r.getObject();
                    Map<String,List<Object>> reply = (Map<String,List<Object>>)map.get("reply");
                    Map<String,List<Object>> replyCreator = (Map<String,List<Object>>)map.get("replyCreator");
                    LdbcShortQuery7MessageRepliesResult ldbcResult = new LdbcShortQuery7MessageRepliesResult(
                            (Long)reply.get("Comment.id").get(0),
                            (String)reply.get("content").get(0),
                            (Long)reply.get("creationDate").get(0),
                            (Long)replyCreator.get("Person.id").get(0),
                            (String)replyCreator.get("firstName").get(0),
                            (String)replyCreator.get("lastName").get(0),
                            false
                    );
                    results.add(ldbcResult);
                }
            }
            resultReporter.report(CODE_OK, results, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
