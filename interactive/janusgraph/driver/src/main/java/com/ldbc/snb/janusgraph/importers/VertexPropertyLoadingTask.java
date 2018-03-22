package com.ldbc.snb.janusgraph.importers;

import com.ldbc.snb.janusgraph.importers.utils.LoadingStats;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.SchemaViolationException;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by aprat on 9/06/17.
 */
public class VertexPropertyLoadingTask extends LoadingTask {

    public StandardJanusGraph graph = null;
    public WorkLoadSchema schema = null;
    public String vertexLabel = null;
    private Logger logger = LoggerFactory.getLogger("org.janusgraph");
    private Function<String, Object> parsers[];
    private String propertyNames[];
    private LoadingStats stats;
    private long numLoaded = 0;

    JanusGraphTransaction transaction;

    public VertexPropertyLoadingTask(StandardJanusGraph graph, WorkLoadSchema schema, String vertexLabel, LoadingStats stats, String header, String[] rows, int numRows) {
        super(header,rows, numRows);
        this.graph = graph;
        this.schema = schema;
        this.vertexLabel = vertexLabel;
        this.stats = stats;
    }

    @Override
    protected void validateHeader(String[] header) throws SchemaViolationException {

        Set<String> props = schema.getVertexProperties().get(vertexLabel);
        if (props == null)
            throw new SchemaViolationException("No properties found for the vertex label " + vertexLabel);

        if (!header[0].equals(vertexLabel+".id") || !props.contains(header[1])) {
            throw new SchemaViolationException("Unknown property for vertex Type" + vertexLabel
                    + ", found " + header[1] + " expected " + props);
        }

        Class[] classes = new Class[header.length];
        for (int i = 0; i < header.length; i++) {
            classes[i] = schema.getVPropertyClass(vertexLabel, header[i]);
        }

        // Obtaining parsers for the fields and property names
        parsers = new Function[header.length];
        propertyNames = new String[header.length];
        for (int i = 1; i < header.length; ++i) {
            parsers[i] = Parsers.getParser(classes[i]);
            propertyNames[i] = vertexLabel + "." + header[i];
        }
        parsers[0] = Parsers.getParser(Long.class);
        propertyNames[0] = vertexLabel+".id";

        transaction = graph.newThreadBoundTransaction()  ;
    }

    @Override
    protected void parseRow(String[] row) {
        Long vertexId = Long.parseLong(row[0]);
        Vertex vertex =  transaction.traversal().V().has(propertyNames[0],vertexId).next();
        if (vertex == null) {
            logger.error("Vertex property update failed, since no vertex with id {} from line {}",row[0]);
            throw new RuntimeException("Vertex "+vertexId+" does not exists");
        }
        for (int i = 1; i < row.length; ++i) {
            Object value = parsers[i].apply(row[i]);
            vertex.property(propertyNames[i], value);
        }
        numLoaded++;
    }

    @Override
    protected void afterRows() {
        transaction.commit();
        stats.addProperties(numLoaded);
    }

}
