package com.hhly.datacenter.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Verson on 2016/11/21.
 */
public class TaskThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final boolean daemon;
    private final int priority;

    public TaskThreadFactory(String namePrefix, boolean daemon, int priority) {
        SecurityManager s = System.getSecurityManager();
        this.group = (s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup());
        this.namePrefix = namePrefix;
        this.daemon = daemon;
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread t = new Thread(this.group,r,this.namePrefix + threadNumber.getAndIncrement());
        t.setDaemon(this.daemon);
        t.setPriority(this.priority);
        return t;

    }

}
