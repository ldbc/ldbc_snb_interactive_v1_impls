package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForum;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery6MessageForumResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 6
 * Given a Message (Post or Comment), retrieve the Forum that contains it and the Person that moderates that forum.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery6Handler implements OperationHandler<LdbcShortQuery6MessageForum,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery6Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery6MessageForum operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.messageId());

        String queryPost = "g.V().has('Post.id',$id)" +
                ".match( __.as('x').in('containerOf').as('y').valueMap().as('forum')," +
                "__.as('y').out('hasModerator').valueMap().as('moderator')).select('forum','moderator')\n";

        String queryComment = "g.V().has('Comment.id',$id)" +
                ".match( __.as('x').until(hasLabel('Post')).repeat(out('replyOf')).in('containerOf').as('y').valueMap().as('forum')," +
                        "__.as('y').out('hasModerator').valueMap().as('moderator')).select('forum','moderator')\n";


        try {
            ResultSet resultSet = dbConnectionState.runQuery(queryPost, parameters);
            Map<String,List<Object>> map = null;
            for (Result r : resultSet) {
                map = (Map<String,List<Object>>)r.getObject();
            }
            if (map == null) {
                resultSet = dbConnectionState.runQuery(queryComment, parameters);
                for (Result r : resultSet) {
                    map = (Map<String,List<Object>>)r.getObject();
                }
            }
            Map<String,List<Object>> forum = (Map<String,List<Object>>)map.get("forum");
            Map<String,List<Object>> moderator = (Map<String,List<Object>>)map.get("moderator");
            LdbcShortQuery6MessageForumResult result = new LdbcShortQuery6MessageForumResult(
                    (Long)forum.get("Forum.id").get(0),
                    (String)forum.get("title").get(0),
                    (Long)moderator.get("Person.id").get(0),
                    (String)moderator.get("firstName").get(0),
                    (String)moderator.get("lastName").get(0)
            );
            resultReporter.report(CODE_OK, result, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
