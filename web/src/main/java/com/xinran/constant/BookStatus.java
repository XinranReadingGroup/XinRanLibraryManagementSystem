
package com.xinran.constant;


/**
 * @author 高海军 帝奇 Apr 3, 2015 7:27:02 PM
 */

public enum BookStatus {

    donatedStatus(0), sharedStatus(1);

    private int status;

    private BookStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
