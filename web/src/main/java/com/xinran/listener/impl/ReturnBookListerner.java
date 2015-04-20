package com.xinran.listener.impl;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.xinran.event.Event;
import com.xinran.listener.Listener;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:18:43 PM
 */
@Configurable(preConstruction = true)
@Service
public class ReturnBookListerner implements Listener {


    @Override
    public void onEvent(Event event) {

    }

}
