package com.hhly.datacenter.etl.sqoop;

/**
 * Created by Verson on 2016/11/21.
 */
public class Job extends AbstractJob implements Runnable {

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
