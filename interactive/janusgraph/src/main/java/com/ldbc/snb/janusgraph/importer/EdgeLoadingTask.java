package com.ldbc.snb.janusgraph.importer;

import com.ldbc.snb.janusgraph.importer.utils.LoadingStats;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.SchemaViolationException;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Function;

import static com.ldbc.snb.janusgraph.importer.JanusGraphImporter.TUPLESPLIT;

/**
 * Created by aprat on 9/06/17.
 */
public class EdgeLoadingTask extends LoadingTask {

    private StandardJanusGraph graph = null;
    private WorkLoadSchema schema = null;
    private String edgeLabel = null;
    private String edgeTail = null;
    private String edgeHead = null;
    private Logger logger = LoggerFactory.getLogger("org.janusgraph");
    private Function<String, Object> parsers[];
    private String propertyNames[];
    private LoadingStats stats;
    private long numLoaded = 0;
    private long numPropertiesLoaded = 0;
    private Vertex lastVertexTail = null;
    private long lastVertexTailId = -1;
    private Vertex lastVertexHead = null;
    private long lastVertexHeadId = -1;

    JanusGraphTransaction transaction;

    public EdgeLoadingTask(StandardJanusGraph graph, WorkLoadSchema schema, String edgeSignature, LoadingStats stats, String header, String[] rows, int numRows) {
        super(header,rows,numRows);
        this.graph = graph;
        this.schema = schema;
        this.stats = stats;
        String [] edgeParts = edgeSignature.split(TUPLESPLIT);
        this.edgeTail = edgeParts[0];
        this.edgeLabel = edgeParts[1];
        this.edgeHead = edgeParts[2];
    }

    @Override
    protected void validateHeader(String[] header) throws SchemaViolationException {

        Set<String> vTypes = schema.getVertexTypes().keySet();
        if (!vTypes.contains(edgeTail) || !vTypes.contains(edgeHead))
            throw new SchemaViolationException("Vertex type not found for triple " + edgeTail +"."+edgeLabel+"."+edgeHead+", found " + vTypes);

        Set<String> eTypes = schema.getEdgeTypes();
        if (!eTypes.contains(edgeLabel))
            throw new SchemaViolationException("Edge type not found for triple" + edgeTail +"."+edgeLabel+"."+edgeHead + ", found " + eTypes);

        //This may be null and that's fine, not all edges have properties
        Set<String> props = schema.getEdgeProperties().get(edgeLabel);

        if (!header[0].equals(edgeTail + ".id"))
            throw new SchemaViolationException("First column is not labeled " + edgeTail + ".id, but:" + header[0]);

        if (!header[1].equals(edgeHead + ".id"))
            throw new SchemaViolationException("Second column is not labeled " + edgeHead + ".id, but:" + header[0]);

        for (String col : header) {
            if (col.contains(".id"))
                continue;

            if (props == null || !props.contains(col))
                throw new SchemaViolationException("Unknown property, found " + col + "expected" + props);
        }

        Class[] classes = new Class[header.length];
        for (int i = 0; i < header.length; i++) {
            classes[i] = schema.getPropertyClass(header[i]);
        }

        // Obtaining parsers for the fields and property names
        parsers = new Function[header.length];
        propertyNames = new String[header.length];

        parsers[0] = Parsers.getParser(Long.class);
        propertyNames[0] = header[0];
        parsers[1] = Parsers.getParser(Long.class);
        propertyNames[1] = header[1];
        for (int i = 2; i < header.length; ++i) {
            parsers[i] = Parsers.getParser(classes[i]);
            if(header[i].compareTo("id") == 0) {
                propertyNames[i] = edgeLabel + "." + header[i];
            } else {
                propertyNames[i] = header[i];
            }
        }
        transaction = graph.newThreadBoundTransaction();
    }

    @Override
    protected void parseRow(String[] row) {
        Object idTail = parsers[0].apply(row[0]);
        Object idHead = parsers[1].apply(row[1]);

        Vertex tail = lastVertexTailId == (Long)(idTail) ? lastVertexTail : transaction.traversal().V().has(propertyNames[0],idTail).next();
        lastVertexTail = tail;
        lastVertexTailId = (Long)idTail;
        Vertex head = lastVertexHeadId == (Long)(idHead) ? lastVertexHead : transaction.traversal().V().has(propertyNames[1],idHead).next();
        lastVertexHead = head;
        lastVertexHeadId = (Long)idHead;
        Edge edge = tail.addEdge(edgeLabel,head);
        for (int i = 2; i < row.length; i++) {
            edge.property(propertyNames[i], parsers[i].apply(row[i]));
            numPropertiesLoaded++;
        }
        numLoaded++;
    }

    @Override
    protected void afterRows() {
        transaction.commit();
        stats.addEdges(numLoaded);
        stats.addProperties(numPropertiesLoaded);
    }

}
