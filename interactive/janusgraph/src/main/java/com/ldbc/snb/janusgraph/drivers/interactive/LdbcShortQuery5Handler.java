package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery5MessageCreatorResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 5
 * Given a Message (Post or Comment), retrieve its author.
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery5Handler implements OperationHandler<LdbcShortQuery5MessageCreator,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery5Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery5MessageCreator operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.messageId());

        String queryPost = "g.V().has('Post.id',$id).out('hasCreator').valueMap()\n";
        String queryComment = "g.V().has('Comment.id',$id).out('hasCreator').valueMap()\n";

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
            LdbcShortQuery5MessageCreatorResult result = new LdbcShortQuery5MessageCreatorResult(
                    (Long)map.get("Person.id").get(0),
                    (String)map.get("firstName").get(0),
                    (String)map.get("lastName").get(0)
            );
            resultReporter.report(CODE_OK, result, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}