package com.xinran.event.impl;

import com.xinran.pojo.BorrowReturnRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xinran.event.AbstractEvent;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:06:15 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnBookEvent extends AbstractEvent {

    private BorrowReturnRecord borrowReturnRecord;


    public ReturnBookEvent(BorrowReturnRecord borrowReturnRecord) {
        this.borrowReturnRecord = borrowReturnRecord;
        super.type=this.getClass().getSimpleName();
    }

}
