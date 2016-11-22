package com.hhly.datacenter.rpc;

import java.util.HashMap;

/**
 * Created by Verson on 2016/11/22.
 */
public class Request {

    private String method;
    private String token;
    private HashMap<String,Object> params;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

}
