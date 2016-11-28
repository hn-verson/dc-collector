package com.hhly.datacenter.etl.sqoop;

import java.util.Stack;
import java.util.UUID;

/**
 * Created by verson on 16-11-28.
 */
public abstract class AbstractJob {

    private String jobId = UUID.randomUUID().toString();

    protected JobState state = JobState.NEW;
    protected Stack<Task> stack = new Stack<>();

    protected enum JobState{
        NEW,RUNNING,SUCCESS,FAILURE
    }

    public String getJobId() {
        return jobId;
    }

}
