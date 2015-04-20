package com.xinran.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xinran.event.AbstractEvent;
import com.xinran.pojo.BorrowReturnRecord;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:05:10 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BorrowBookEvent extends AbstractEvent {

    private BorrowReturnRecord borrowReturnRecord;
    /**
     * @param type
     */
    public BorrowBookEvent(String type) {
        super(BorrowBookEvent.class.getName());
    }

    public BorrowBookEvent(BorrowReturnRecord borrowReturnRecord) {
        super(BorrowBookEvent.class.getName());
        this.borrowReturnRecord = borrowReturnRecord;
    }


}
