package com.ldbc.snb.janusgraph.drivers.interactive;

import com.ldbc.driver.DbException;
import com.ldbc.driver.OperationHandler;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate3AddCommentLike;
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
 * Adds like from person to comment, assumes person and comment exist
 * Created by Tomer Sagi on 14-Nov-14.
 */
public class LdbcQueryU3Handler implements OperationHandler<LdbcUpdate3AddCommentLike,JanusGraphDb.RemoteDBConnectionState> {
    final static Logger logger = LoggerFactory.getLogger(LdbcQueryU3Handler.class);

    @Override
    public void executeOperation(LdbcUpdate3AddCommentLike operation, JanusGraphDb.RemoteDBConnectionState dbConnectionState,
                                 ResultReporter reporter) throws DbException {

        StandardJanusGraph graph = dbConnectionState.getGraph();
        JanusGraphTransaction transaction = graph.newThreadBoundTransaction();

        Vertex personVertex = transaction.traversal().V().has("Person.id", operation.personId()).next();
        Vertex postVertex = transaction.traversal().V().has("Comment.id", operation.commentId()).next();
        Edge edge = personVertex.addEdge("likes",postVertex);
        edge.property("creationDate", operation.creationDate().getTime());

        transaction.commit();

        reporter.report(0, LdbcNoResult.INSTANCE,operation);
    }
}
