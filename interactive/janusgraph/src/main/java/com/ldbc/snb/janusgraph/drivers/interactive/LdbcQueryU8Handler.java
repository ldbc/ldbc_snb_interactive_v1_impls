package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate8AddFriendship;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Add knows edge between two given people assumed to exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU8Handler implements OperationHandler<LdbcUpdate8AddFriendship,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU8Handler.class);

    @Override
    public void executeOperation(LdbcUpdate8AddFriendship operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter reporter) throws DbException {


        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex personVertex1 = transaction.traversal().V().has("Person.id", operation.person1Id()).next();
        Vertex personVertex2 = transaction.traversal().V().has("Person.id", operation.person2Id()).next();
        Edge friendship = personVertex1.addEdge("knows",personVertex2);
        friendship.property("creationDate",operation.creationDate().getTime());

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
