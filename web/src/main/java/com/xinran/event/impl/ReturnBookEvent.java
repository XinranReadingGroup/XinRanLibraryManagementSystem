package com.xinran.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xinran.event.AbstractEvent;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:06:15 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnBookEvent extends AbstractEvent {

    /**
     * @param type
     */
    public ReturnBookEvent(String type) {
        super(ReturnBookEvent.class.getName());
    }

}
