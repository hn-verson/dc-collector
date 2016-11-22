package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.rpc.Handler;
import com.hhly.datacenter.rpc.Request;
import com.hhly.datacenter.rpc.Response;
import com.hhly.datacenter.rpc.ResponseStatus;

/**
 * Created by Verson on 2016/11/22.
 */
public class SqoopHandler implements Handler {

    @Override
    public void handle(Request req, Response res) {
        if (!validate(req)) {
            res.setStatus(ResponseStatus.FAILURE.getStatus());
            res.setMessage(ResponseStatus.FAILURE.getMessage());
        }
    }

    private boolean validate(Request req){
        return false;
    }

}
