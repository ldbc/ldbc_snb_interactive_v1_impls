package com.ldbc.snb.janusgraph.importers;

import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.SchemaViolationException;
import org.janusgraph.graphdb.database.StandardJanusGraph;
import org.janusgraph.graphdb.tinkerpop.JanusGraphBlueprintsGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Created by aprat on 13/06/17.
 */
public abstract class LoadingTask implements Runnable {

    private final static String CSVSPLIT = "\\|";
    private boolean executed = false;
    private String header;
    private String [] rows;
    private int numRows;


    public LoadingTask(String header, String [] rows, int numRows) {
        this.header = header;
        this.rows = rows;
        this.numRows = numRows;
    }

    protected abstract void validateHeader(String[] header);

    protected abstract void parseRow(String [] row);
    protected abstract void afterRows();

    public void run() {

        //Read title line and map to vertex properties, throw exception if doesn't match
        String[] columnNames = header.split(CSVSPLIT);
        validateHeader(columnNames);

        //Read and load rest of file
        for( int i = 0; i < numRows; ++i) {
            String[] cells = rows[i].split(CSVSPLIT);
            parseRow(cells);
        }
        afterRows();
        executed = true;
    }

    public boolean isExecuted() { return executed; }
}
