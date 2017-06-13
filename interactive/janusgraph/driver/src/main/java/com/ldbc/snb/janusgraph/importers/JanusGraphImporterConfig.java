package com.ldbc.snb.janusgraph.importers;

import com.beust.jcommander.Parameter;

/**
 * Created by aprat on 13/06/17.
 */
public class JanusGraphImporterConfig {

    @Parameter(names = {"--numThreads","-n"}, description = "Number of worker threads to use to read the input files")
    private int numThreads = 1;

    @Parameter(names = {"--transactionSize", "-s"}, description = "The maximum size of a transaction")
    private int transactionSize = 1000;

    public int getNumThreads() {
        return numThreads;
    }

    public int getTransactionSize() {
        return transactionSize;
    }
}
