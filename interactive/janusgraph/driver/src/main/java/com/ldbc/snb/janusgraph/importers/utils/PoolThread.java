package com.ldbc.snb.janusgraph.importers.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by aprat on 9/06/17.
 */
public class PoolThread extends Thread {

    private BlockingQueue   taskQueue = null;
    private boolean         isStopped = false;

    public PoolThread(BlockingQueue queue){
        taskQueue = queue;
    }

    public void run(){
        while(!isStopped()){
            try{
                Runnable task = (Runnable)taskQueue.poll(1000, TimeUnit.MILLISECONDS);
                if(task!=null)
                    task.run();
            } catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop(){
        isStopped = true;
        //this.interrupt(); //break pool thread out of dequeue() call.
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }

}