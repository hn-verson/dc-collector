package com.hhly.datacenter.rpc;

/**
 * Created by Verson on 2016/11/22.
 */
public enum ResponseStatus {

    SUCCESS("success",200),
    FAILURE("failure",300),
    TIMEOUT("timeout",400);

    private String message;
    private int status;

    ResponseStatus(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
