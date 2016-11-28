package com.hhly.datacenter.rpc;

/**
 * Created by Verson on 2016/11/22.
 */
public interface Handler {

    void process(Request req,Response res);

}
