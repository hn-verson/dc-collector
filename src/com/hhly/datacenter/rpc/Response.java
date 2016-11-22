package com.hhly.datacenter.rpc;

/**
 * Created by Verson on 2016/11/22.
 */
public class Response {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
