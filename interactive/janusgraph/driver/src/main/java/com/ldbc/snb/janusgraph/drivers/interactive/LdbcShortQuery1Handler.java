package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcQuery1Result;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfile;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcShortQuery1PersonProfileResult;
import org.slf4j.*;

import javax.naming.directory.SchemaViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of LDBC Interactive workload short query 1
 * A person's properties
 * Created by Tomer Sagi on 10-Mar-15.
 */
public class LdbcShortQuery1Handler implements OperationHandler<LdbcShortQuery1PersonProfile,JanusGraphDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcShortQuery1Handler.class);

    @Override
    public void executeOperation(final LdbcShortQuery1PersonProfile operation, JanusGraphDb.BasicDbConnectionState dbConnectionState, ResultReporter resultReporter) throws DbException {
       /* List<LdbcQuery1Result> result = new ArrayList<>();
        long person_id = operation.personId();
        JanusGraphDb.BasicClient client = dbConnectionState.client();
        final Vertex root;
        try {
            root = client.getVertex(person_id, "Person");
            logger.debug("Short Query 1 called on person id: {}", person_id);

            Vertex cityV = QueryUtils.getPersonCity(root);
            LdbcShortQuery1PersonProfileResult res = new LdbcShortQuery1PersonProfileResult(
                    (String) root.getProperty("firstName"),(String) root.getProperty("lastName"),
                    (Long) root.getProperty("birthday"), (String) root.getProperty("locationIP"),
                    (String) root.getProperty("browserUsed"),client.getVLocalId((Long)cityV.getId()), (String) root.getProperty("gender"),
                    (Long) root.getProperty("creationDate"));

            resultReporter.report(result.size(), res, operation);
        } catch (SchemaViolationException e) {
        e.printStackTrace();
        resultReporter.report(-1, null, operation);
    }
    */

    }
}