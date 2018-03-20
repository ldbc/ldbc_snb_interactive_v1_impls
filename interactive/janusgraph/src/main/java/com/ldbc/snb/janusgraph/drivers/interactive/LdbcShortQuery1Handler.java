package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfileResult;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.slf4j.*;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Implementation of LDBC Interactive workload short query 1
 * A person's properties
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery1Handler implements OperationHandler<LdbcShortQuery1PersonProfile,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery1Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery1PersonProfile operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter resultReporter) throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.personId());

        String query = "g.V().has('Person.id',$id)." +
                             "match(__.as('person').valueMap().as('x'), " +
                                   "__.as('person').out('isLocatedIn').valueMap('Place.id').as('y'))" +
                                   ".select('x','y')";

        try {
            ResultSet resultSet = dbConnectionState.runQuery(query, parameters);
            ArrayList<LdbcShortQuery1PersonProfileResult> results = new ArrayList<LdbcShortQuery1PersonProfileResult>();
            for (Result r : resultSet) {
                Map<String,List<Object>>  map = (Map<String,List<Object>>)r.getObject();
                Map<String,List<Object>> personMap = (Map<String,List<Object>>)map.get("x");
                Map<String,List<Object>> cityMap = (Map<String,List<Object>>)map.get("y");
                LdbcShortQuery1PersonProfileResult ldbcResult = new LdbcShortQuery1PersonProfileResult(
                        (String)personMap.get("firstName").get(0),
                        (String)personMap.get("lastName").get(0),
                        (Long)personMap.get("birthday").get(0),
                        (String)personMap.get("locationIP").get(0),
                        (String)personMap.get("browserUsed").get(0),
                        (Long)cityMap.get("Place.id").get(0),
                        (String)personMap.get("gender").get(0),
                        (Long)personMap.get("creationDate").get(0));

                results.add(ldbcResult);
            }
            resultReporter.report(CODE_OK, results.get(0), operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}