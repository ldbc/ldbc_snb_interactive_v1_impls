package com.ldbc.snb.janusgraph.importers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aprat on 13/06/17.
 */
public class StatsReportingThread extends Thread {

    private Logger logger = LoggerFactory.getLogger("org.janusgraph");
    private LoadingStats stats;
    private LoadingStats lastStats;
    private long start;
    private long sleepTime = 1000;

    public StatsReportingThread(LoadingStats stats, long sleepTime) {
        this.stats = stats;
        this.sleepTime = sleepTime;
        this.lastStats = new LoadingStats();
    }

    @Override
    public void run() {
        start = System.currentTimeMillis();
        while(true) {
            long current = System.currentTimeMillis();
            long loadedVerticesInterval = stats.getNumVertices() - lastStats.getNumVertices();
            long loadedEdgesInterval = stats.getNumEdges() - lastStats.getNumEdges();
            long loadedVerticesRate = (current - start) > 0 ? (loadedVerticesInterval*1000) / (current - start) : 0;
            long loadedEdgesRate = (current - start) > 0 ? (loadedEdgesInterval*1000) / (current - start) : 0;
            logger.info("Vertices Loaded {}, Edges Loaded {}, Current vertices loaded/s {}, Current edges loaded/s {}",stats.getNumVertices(), stats.getNumEdges(), loadedVerticesRate, loadedEdgesRate);
            lastStats.setNumVertices(stats.getNumVertices());
            lastStats.setNumEdges(stats.getNumEdges());
            start = current;
            try {
                Thread.sleep(sleepTime);
            } catch( Exception e) {
                e.printStackTrace();
            }
        }

    }
}
