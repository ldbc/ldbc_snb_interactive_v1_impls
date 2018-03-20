package com.ldbc.snb.janusgraph.importer;

import com.beust.jcommander.Parameter;

/**
 * Created by aprat on 13/06/17.
 */
public class JanusGraphImporterConfig {

    @Parameter(names = {"--numThreads","-n"}, description = "Number of worker threads to use to read the input files")
    private int numThreads = 1;

    @Parameter(names = {"--transactionSize", "-s"}, description = "The maximum size of a transaction")
    private int transactionSize = 1000;

    @Parameter(names = {"--dataset", "-d"}, description = "The path of to the dataset to load")
    private String dataset;

    @Parameter(names = {"--backend-config", "-c"}, description = "The path to the backend config file")
    private String backendConfigFile;

    public int getNumThreads() {
        return numThreads;
    }

    public int getTransactionSize() {
        return transactionSize;
    }

    public String getDataset() { return dataset; }

    public String getBackendConfigFile() { return backendConfigFile;}
}
