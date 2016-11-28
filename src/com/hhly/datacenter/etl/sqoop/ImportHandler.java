package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.rpc.*;
import java.util.HashMap;

/**
 * Created by Verson on 2016/11/22.
 */
public class ImportHandler implements Handler {

    private ImportTaskConfig taskConfig;

    @Override
    public void process(Request req, Response res) {

        if (!preHandle(req)) {
            res.setStatus(ResponseStatus.FAILURE.getStatus());
            res.setMessage(ResponseStatus.FAILURE.getMessage());
        }

    }

    private boolean preHandle(Request request) {

        taskConfig = (ImportTaskConfig)ConfigManager.getTaskConfig(request.getMethod());

        HashMap<String,Object> requestParams = request.getParams();
        if (requestParams == null || requestParams.size() == 0) {
            return false;
        }

        if (taskConfig.isIncrement()) {
            String[] configParams = taskConfig.getParams();
            for (String param : configParams) {
                if (!requestParams.containsKey(param))
                    return false;
            }
        }

        return true;

    }

}
