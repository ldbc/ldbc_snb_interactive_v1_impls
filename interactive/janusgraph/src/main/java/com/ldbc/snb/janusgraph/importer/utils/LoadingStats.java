package com.ldbc.snb.janusgraph.importer.utils;

/**
 * Created by aprat on 13/06/17.
 */
public class LoadingStats {

    private long numVertices = 0;
    private long numEdges = 0;
    private long numProperties = 0;

    public synchronized void addVertices( long num ) {
        numVertices+=num;
    }

    public synchronized void addEdges( long num ) {
        numEdges+=num;
    }

    public synchronized void addProperties(long num) {numProperties+=num;}

    public synchronized long getNumVertices() {
        return numVertices;
    }

    public synchronized long getNumEdges() {
        return numEdges;
    }

    public synchronized long getNumProperties() { return numProperties;}

    public void setNumVertices(long numVertices) {
        this.numVertices = numVertices;
    }

    public void setNumEdges(long numEdges) {
        this.numEdges = numEdges;
    }

    public void setNumProperties(long numProperties) { this.numProperties = numProperties;}
}
