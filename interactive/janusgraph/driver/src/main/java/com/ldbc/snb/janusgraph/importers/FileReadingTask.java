package com.ldbc.snb.janusgraph.importers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

/**
 * Created by aprat on 13/06/17.
 */
public abstract class FileReadingTask<T> implements Runnable  {

    private String fileName;
    private BlockingQueue<T> outputQueue;
    private int blockSize;
    protected String label;
    private boolean executed = false;
    private Logger logger = LoggerFactory.getLogger("org.janusgraph");

    public FileReadingTask(String fileName, String label, BlockingQueue<T> outputQueue, int blockSize ) {
        this.fileName = fileName;
        this.label = label;
        this.outputQueue = outputQueue;
        this.blockSize = blockSize;
    }

    protected abstract T createTask(String header, String[] rows);

    @Override
    public void run() {
      try {
          logger.info("Reading file {}",fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String header = reader.readLine();
            if(header != null) {
                String [] rows = new String[blockSize];
                String line;
                int counter = 0;
                while ( (line = reader.readLine()) != null) {
                    rows[counter] = line;
                    counter++;
                    if(counter >= blockSize) {
                        T task = createTask(header,rows);
                        outputQueue.put(task);
                        counter = 0;
                    }
                }

                if(counter > 0) {
                    T task = createTask(header,rows);
                    outputQueue.put(task);
                }

            }
            logger.info("Finished reading file {}",fileName);
            reader.close();
        } catch( Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isExecuted() { return executed; }
}
