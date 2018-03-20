package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.sparksee.workloads.ldbc.snb.interactive.db.LDBCppQueryTranslator;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.ALL_MATCH;
import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;
import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.LIST_MATCH;

/**
 * Implementation of LDBC Interactive workload query 1
 * @author Tomer Sagi
 * @modified Arnau Prat
 */
public class LdbcQuery1Handler implements OperationHandler<LdbcQuery1,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQuery1Handler.class);

    //@Override
    public void executeOperation(final LdbcQuery1 operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter resultReporter)
            throws DbException {

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("$id",operation.personId());
        parameters.put("$name",operation.firstName());
        parameters.put("$limit",operation.limit());

        String query = "g.V().has('Person.id', $id)." +
                       "repeat(out('knows').simplePath())." +
                       "emit().times(3)." +
                       "has('firstName',$name).dedup().limit($limit)." +
                       "valueMap()\n";
        try {
            ResultSet resultSet = dbConnectionState.runQuery(query, parameters);
            ArrayList<LdbcQuery1Result> results = new ArrayList<LdbcQuery1Result>();
            for (Result r : resultSet) {
               Map<String,List<Object>>  map = (Map<String,List<Object>>)r.getObject();
               LdbcQuery1Result ldbcQuery1Result = new LdbcQuery1Result((Long)map.get("Person.id").get(0),
                                                                        (String)map.get("lastName").get(0),
                                                                        0,
                                                                        (Long)map.get("birthday").get(0),
                                                                        (Long)map.get("creationDate").get(0),
                                                                        (String)map.get("gender").get(0),
                                                                        (String)map.get("browserUsed").get(0),
                                                                        (String)map.get("locationIP").get(0),
                                                                        new ArrayList<String>(),
                                                                        new ArrayList<String>(),
                                                                        "city",
                                                                        new ArrayList<List<Object>>(),
                                                                        new ArrayList<List<Object>>());
               results.add(ldbcQuery1Result);
            }
            resultReporter.report(CODE_OK, results, operation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}