package hpl.alp2.titan.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.thinkaurelius.titan.core.TitanException;
import com.thinkaurelius.titan.core.TitanVertex;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdVertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomer Sagi on 14-Nov-14.
 * Add a person. Currently assuming all edge end point vertices exist
 */
public class LdbcQueryU1Handler implements OperationHandler<LdbcUpdate1AddPerson,TitanFTMDb.BasicDbConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU1Handler.class);

    @Override
    public void executeOperation(LdbcUpdate1AddPerson operation, TitanFTMDb.BasicDbConnectionState dbConnectionState, ResultReporter reporter) throws DbException {
        TitanFTMDb.BasicClient client = dbConnectionState.client();
        Map<String, Object> props = new HashMap<>(9);
        props.put("firstName", operation.personFirstName());
        props.put("lastName", operation.personLastName());
        props.put("gender", operation.gender());
        props.put("birthday", operation.birthday().getTime());
        props.put("creationDate", operation.creationDate().getTime());
        props.put("locationIP", operation.locationIp());
        props.put("browserUsed", operation.browserUsed());
        logger.debug("U1 Adding person {} , {}" ,operation.personId(), props.toString());
        Vertex person = client.addVertex(operation.personId(), "Person", props);
        TitanVertex v = ((TitanVertex)(((IdVertex) person).getBaseVertex()));
        for (String l : operation.languages())
            v.addProperty("language",l);

        for (String em : operation.emails())
            v.addProperty("email",em);

        try {
            for (LdbcUpdate1AddPerson.Organization org : operation.studyAt()) {

                Vertex orgV = client.getVertex(org.organizationId(), "Organisation");
                Map<String, Object> eProps = new HashMap<>();
                eProps.put("classYear", org.year());
                client.addEdge(person, orgV, "studyAt", eProps);

            }

        } catch (Exception e) {
            logger.error("add university failed");
            e.printStackTrace();
        }
        try {
        for (LdbcUpdate1AddPerson.Organization org : operation.workAt()) {
                Vertex orgV = client.getVertex(org.organizationId(), "Organisation");
                Map<String, Object> eProps = new HashMap<>();
                eProps.put("workFrom", org.year());
                client.addEdge(person, orgV, "workAt", eProps);
            }
        } catch (Exception e) {
            logger.error("add company failed");
            e.printStackTrace();
        }
        try {
            Vertex cityV = client.getVertex(operation.cityId(), "Place");
            client.addEdge(person, cityV, "isLocatedIn", new HashMap<String, Object>());

        } catch (Exception e) {
            logger.error("add city failed");
            e.printStackTrace();
        }

        try {
            client.commit();
        } catch (TitanException e) {
            logger.error("Couldn't complete U1 handler, db didn't commit");
            e.printStackTrace();
        }

        logger.debug("U1 complete for person {}" ,operation.personId());
        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
