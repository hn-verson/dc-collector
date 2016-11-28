package com.hhly.datacenter.etl.sqoop;

import com.hhly.datacenter.rpc.AbstractHandler;
import com.hhly.datacenter.rpc.Handler;
import com.hhly.datacenter.rpc.Request;
import com.hhly.datacenter.rpc.Response;

/**
 * Created by verson on 16-11-28.
 */
public class ExportHandler extends AbstractHandler implements Handler {

    @Override
    public void process(Request req, Response res) {

    }

    @Override
    protected boolean validate(Request request) {
        return super.validate(request);
    }

}
