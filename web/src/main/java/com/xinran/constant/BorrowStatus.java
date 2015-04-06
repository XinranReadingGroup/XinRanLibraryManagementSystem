/*
 * Copyright 1999-2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
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
