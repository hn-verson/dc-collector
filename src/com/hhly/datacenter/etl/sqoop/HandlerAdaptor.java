package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.rpc.Handler;
import com.hhly.datacenter.rpc.Request;
import com.hhly.datacenter.rpc.Response;
import com.hhly.datacenter.rpc.ResponseStatus;

/**
 * Created by verson on 16-11-28.
 */
public class HandlerAdaptor implements Handler {

    @Override
    public void process(Request req, Response res) {

        TaskConfig taskConfig = ConfigManager.getTaskConfig(req.getMethod());
        Class taskHandlerClass = getTaskHandleClass(taskConfig.getType());
        if (taskHandlerClass == null || !Handler.class.isAssignableFrom(taskHandlerClass))
            return;

        try {
            Handler taskHandler = (Handler)taskHandlerClass.newInstance();
            taskHandler.process(req,res);
        } catch (Exception  e) {
            //TODO
            res.setStatus(ResponseStatus.FAILURE.getStatus());
            res.setMessage(ResponseStatus.FAILURE.getMessage());
        }

    }

    private Class getTaskHandleClass(String taskType){

        if (TaskType.IMPORT.equals(taskType))
            return TaskType.IMPORT.getTaskHandlerClazz();
        else if (TaskType.CLEAN.equals(taskType))
            return TaskType.CLEAN.getTaskHandlerClazz();
        else if (TaskType.EXPORT.equals(taskType))
            return TaskType.EXPORT.getTaskHandlerClazz();
        else
            return null;

    }

}
