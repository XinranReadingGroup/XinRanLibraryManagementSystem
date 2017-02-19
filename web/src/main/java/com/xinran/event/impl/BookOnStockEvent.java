package com.xinran.event.impl;

import com.xinran.event.AbstractEvent;
import com.xinran.pojo.OnOffStockRecord;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:05:10 PM
 */
public class BookOnStockEvent extends AbstractEvent {


    private OnOffStockRecord onOffStockRecord;
    public BookOnStockEvent(OnOffStockRecord onOffStockRecord) {
       this.onOffStockRecord = onOffStockRecord;
       super.type=  this.getClass().getSimpleName();
    }

    public OnOffStockRecord getOnOffStockRecord() {
        return onOffStockRecord;
    }


}
