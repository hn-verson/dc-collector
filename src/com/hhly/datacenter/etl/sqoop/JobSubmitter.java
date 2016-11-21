package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.util.TaskThreadFactory;
import java.util.concurrent.*;

/**
 * Created by Verson on 2016/11/21.
 */
public class JobSubmitter {

    /**
     * External Executor based thread pool.
     */
    private Executor executor = null;

    private int minSpareThreads = 10;

    /**
     * Maximum amount of worker threads.
     */
    private int maxThreads = 200;

    private boolean daemon = false;

    /**
     * Priority of the worker threads.
     */
    protected int threadPriority = Thread.NORM_PRIORITY;

    private boolean initialized = false;

    public void init(){
        createExecutor();
        initialized = true;
    }

    public void createExecutor(){
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        ThreadFactory tf = new TaskThreadFactory("etl-exec-",daemon,threadPriority);
        executor = new ThreadPoolExecutor(getMinSpareThreads(),getMaxThreads(),60,TimeUnit.SECONDS,taskQueue,tf);
    }

    public int getMinSpareThreads() {
        return Math.min(minSpareThreads,maxThreads);
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void submit(Job job){

        if (!initialized)
            init();

        executor.execute(job);

    }

}
