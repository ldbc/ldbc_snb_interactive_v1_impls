package com.ldbc.snb.janusgraph.importers.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by aprat on 9/06/17.
 */

public class ThreadPool {

    private BlockingQueue taskQueue = null;
    private List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks){
        taskQueue = new ArrayBlockingQueue<Runnable>(maxNoOfTasks);

        for(int i=0; i<noOfThreads; i++){
            threads.add(new PoolThread(taskQueue));
        }
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public synchronized void  execute(Runnable task) throws Exception{
        if(this.isStopped) throw
            new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.put(task);
    }

    public synchronized void stop(){
        while(!taskQueue.isEmpty()) {
            try{
                Thread.sleep(1000);
            } catch( InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isStopped = true;
        for(PoolThread thread : threads){
           thread.doStop();
        }

        try {
            for (PoolThread thread : threads) {
                thread.join();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue getTaskQueue() {
        return taskQueue;
    }

}