package com.hhly.datacenter.etl.sqoop;

import java.util.Map;

/**
 * Created by Verson on 2016/11/21.
 */
public class Task {

    private TaskConfig config;
    private Job job;
    private TaskState state = TaskState.NEW;
    private Map params;

    protected enum TaskState{
        NEW,RUNNING,SUCCESS,FAILURE
    }

    public void start(){

        if (config == null || job == null || state != TaskState.NEW)
            return;

        state = TaskState.RUNNING;

        //TODO

    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setConfig(TaskConfig config) {
        this.config = config;
    }

}
