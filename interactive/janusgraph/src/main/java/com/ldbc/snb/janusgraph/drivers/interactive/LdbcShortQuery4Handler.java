package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContent;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery4MessageContentResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 4
 * Given a Message (Post or Comment), retrieve its content and creation date.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery4Handler implements OperationHandler<LdbcShortQuery4MessageContent,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery4Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery4MessageContent operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.messageId());

        String queryPost = "g.V().has('Post.id',$id).valueMap()\n";
        String queryComment = "g.V().has('Comment.id',$id).valueMap()\n";

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
            LdbcShortQuery4MessageContentResult result = new LdbcShortQuery4MessageContentResult(
                    (String)map.get("content").get(0),
                    (Long)map.get("creationDate").get(0)
            );
            resultReporter.report(CODE_OK, result, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}