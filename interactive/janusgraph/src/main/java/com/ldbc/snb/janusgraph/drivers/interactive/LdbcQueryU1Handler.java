package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.Transaction;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ldbc.snb.janusgraph.drivers.interactive.QueryUtils.CODE_OK;

/**
 * Created by Tomer Sagi on 14-Nov-14.
 * Add a person. Currently assuming all edge end point vertices exist
 */
public class LdbcQueryU1Handler implements OperationHandler<LdbcUpdate1AddPerson,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU1Handler.class);

    @Override
    public void executeOperation(LdbcUpdate1AddPerson operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter
            reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        JanusGraphVertex vertex = transaction.addVertex("Person");
        vertex.property("Person.id", operation.personId());
        vertex.property("firstName", operation.personFirstName());
        vertex.property("lastName", operation.personLastName());
        vertex.property("gender", operation.gender());
        vertex.property("birthday", operation.birthday().getTime());
        vertex.property("creationDate", operation.creationDate().getTime());
        vertex.property("browserUsed", operation.browserUsed());
        vertex.property("locationIP", operation.locationIp());


        Vertex cityVertex = transaction.traversal().V().has("Place.id", operation.cityId()).next();
        vertex.addEdge("isLocatedIn",cityVertex);

        Object languages[] = new Object[operation.languages().size()];
        int i = 0;
        for( String language : operation.languages()) {
            languages[i] = language;
            ++i;
        }
        vertex.property("language", languages);

        Object emails[] = new Object[operation.emails().size()];
        i = 0;
        for( String email : operation.emails()) {
            emails[i] = email;
            ++i;
        }
        vertex.property("email", emails);

        for(Long tagId : operation.tagIds()) {
            Vertex tagVertex = transaction.traversal().V().has("Tag.id", tagId).next();
            vertex.addEdge("hasInterest",tagVertex);
        }

        for(LdbcUpdate1AddPerson.Organization organization : operation.studyAt()) {
            Vertex organisationVertex = transaction.traversal().V().has("Organisation.id", organization.organizationId()).next();
            Edge edge = vertex.addEdge("studyAt",organisationVertex);
            edge.property("classYear",organization.year());
        }

        for(LdbcUpdate1AddPerson.Organization organization : operation.workAt()) {
            Vertex organisationVertex = transaction.traversal().V().has("Organisation.id", organization.organizationId()).next();
            Edge edge = vertex.addEdge("workAt",organisationVertex);
            edge.property("workFrom",organization.year());
        }

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
