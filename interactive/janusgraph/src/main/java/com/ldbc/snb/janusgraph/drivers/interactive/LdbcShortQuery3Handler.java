package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriends;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery3PersonFriendsResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 3
 Given a start Person, retrieve all of their friends, and the date at which they became friends.
 Order results descending by friendship creation date, then ascending by friend identifier
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery3Handler implements OperationHandler<LdbcShortQuery3PersonFriends,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery3Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery3PersonFriends operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.personId());

        String query = "g.V().has('Person.id',$id)" +
                ".match(__.as('x').out('knows').valueMap('firstName', 'lastName','Person.id').as('friend'), " +
                       "__.as('x').outE('knows').valueMap('creationDate').as('friendship'))" +
                ".select('friend','friendship')\n";

        try {
            ResultSet resultSet = dbConnectionState.runQuery(query, parameters);
            ArrayList<LdbcShortQuery3PersonFriendsResult> results = new ArrayList<LdbcShortQuery3PersonFriendsResult>();
            for (Result r : resultSet) {
                Map<String,List<Object>> map = (Map<String,List<Object>>)r.getObject();
                Map<String,List<Object>> friend = (Map<String,List<Object>>)map.get("friend");
                Map<String,Object> friendship = (Map<String,Object>)map.get("friendship");
                Long messageId = 0L;
                LdbcShortQuery3PersonFriendsResult ldbcResult = new LdbcShortQuery3PersonFriendsResult(
                        (Long)friend.get("Person.id").get(0),
                        (String)friend.get("firstName").get(0),
                        (String)friend.get("lastName").get(0),
                        (Long)(friendship.get("creationDate")));

                results.add(ldbcResult);
            }
            resultReporter.report(CODE_OK, results, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

}