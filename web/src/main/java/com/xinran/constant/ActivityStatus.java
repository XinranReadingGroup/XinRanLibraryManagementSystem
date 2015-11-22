package com.xinran.constant;

/**
 * Created by gsy on 2015/11/22.
 */
public enum ActivityStatus {

    PUBLISH(0),

    CANCEL(1),

    ;

    private int status;

    private ActivityStatus(int status) {
        this.status = Integer.valueOf(status);
    }

    public int getStatus() {
        return status;
    }
}
