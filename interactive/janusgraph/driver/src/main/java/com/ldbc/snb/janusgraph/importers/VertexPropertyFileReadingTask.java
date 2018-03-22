package com.ldbc.snb.janusgraph.importers;

import com.ldbc.snb.janusgraph.importers.utils.LoadingStats;
import org.janusgraph.graphdb.database.StandardJanusGraph;

import java.util.concurrent.BlockingQueue;

/**
 * Created by aprat on 13/06/17.
 */
public class VertexPropertyFileReadingTask extends FileReadingTask<VertexPropertyLoadingTask> {

    private StandardJanusGraph graph = null;
    private WorkLoadSchema schema = null;
    private LoadingStats stats;

    public VertexPropertyFileReadingTask(StandardJanusGraph graph, WorkLoadSchema schema, String fileName, String label, BlockingQueue<VertexPropertyLoadingTask> outputQueue, int blockSize, LoadingStats stats) {
        super(fileName, label, outputQueue, blockSize);
        this.graph = graph;
        this.schema = schema;
        this.stats = stats;
    }

    @Override
    protected VertexPropertyLoadingTask createTask(String header, String[] rows, int numRows) {
        return new VertexPropertyLoadingTask(graph,schema,label,stats,header,rows, numRows);
    }
}
