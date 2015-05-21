package com.xinran.event.impl;

import com.xinran.event.AbstractEvent;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:05:10 PM
 */
public class BookOnStockEvent extends AbstractEvent {

    /**
     * @param type
     */
    public BookOnStockEvent(String type) {
        super(type);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param type
     */
    public BookOnStockEvent() {
        super(BookOnStockEvent.class.getName());
    }


}
