package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate2AddPostLike;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Adds like, assumes person and post exist.
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU2Handler implements OperationHandler<LdbcUpdate2AddPostLike,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU2Handler.class);

    @Override
    public void executeOperation(LdbcUpdate2AddPostLike operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState, ResultReporter
            reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex personVertex = transaction.traversal().V().has("Person.id", operation.personId()).next();
        Vertex postVertex = transaction.traversal().V().has("Post.id", operation.postId()).next();
        Edge edge = personVertex.addEdge("likes",postVertex);
        edge.property("creationDate", operation.creationDate().getTime());

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);

    }
}
