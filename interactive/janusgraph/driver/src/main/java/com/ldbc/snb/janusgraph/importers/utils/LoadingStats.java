package com.ldbc.snb.janusgraph.importers.utils;

/**
 * Created by aprat on 13/06/17.
 */
public class LoadingStats {

    private long numVertices = 0;
    private long numEdges = 0;

    public synchronized void addVertices( long num ) {
        numVertices+=num;
    }

    public synchronized void addEdges( long num ) {
        numEdges+=num;
    }

    public synchronized long getNumVertices() {
        return numVertices;
    }

    public synchronized long getNumEdges() {
        return numEdges;
    }

    public void setNumVertices(long numVertices) {
        this.numVertices = numVertices;
    }

    public void setNumEdges(long numEdges) {
        this.numEdges = numEdges;
    }
}
