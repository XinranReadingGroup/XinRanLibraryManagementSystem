
package com.xinran.constant;


/**
 * @author 高海军 帝奇 Apr 3, 2015 7:27:02 PM
 */

public enum BorrowStatus {

    borrowedStatus(1), notBorrowedStatus(0);

    private int status;

    private BorrowStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}