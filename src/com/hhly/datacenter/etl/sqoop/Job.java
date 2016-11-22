package com.hhly.datacenter.etl.sqoop;

import java.util.Stack;
import java.util.UUID;

/**
 * Created by Verson on 2016/11/21.
 */
public class Job implements Runnable {

    private String jobId = UUID.randomUUID().toString();
    private JobState state = JobState.NEW;
    private Stack<Task> stack = new Stack<>();

    protected enum JobState{
        NEW,RUNNING,SUCCESS,FAILURE
    }

    public void start(){

        if (stack.size() == 0)
            return;

        if (state != JobState.NEW)
            return;

        state = JobState.RUNNING;

        while (!stack.isEmpty()) {
            stack.pop().start();
        }

        state = JobState.SUCCESS;

    }

    @Override
    public void run() {
        start();
    }

}
