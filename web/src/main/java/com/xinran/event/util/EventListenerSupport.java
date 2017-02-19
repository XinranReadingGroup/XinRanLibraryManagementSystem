package com.xinran.event.util;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.xinran.event.Event;
import com.xinran.listener.Listener;

/**
 * @author 高海军 帝奇 Apr 18, 2015 11:12:08 AM
 */
public abstract class EventListenerSupport {

    private static Logger                                             log    = LoggerFactory.getLogger(EventListenerSupport.class);

    
    private static Map<Class<?>, CopyOnWriteArrayList<Listener>> holder = Maps.newHashMap();

    //重要: 在ApplicationBootstrap 这个类里面完成监听器设置。
    public static void addListener(Class<?> event, Listener listener) {
        CopyOnWriteArrayList<Listener> arrayList = holder.get(event);
        if (CollectionUtils.isEmpty(arrayList)) {
            arrayList = new CopyOnWriteArrayList<Listener>();
            arrayList.add(listener);
            holder.put(event, arrayList);
        } else {
            arrayList.add(listener);
        }
    }

    // public static void removeListener(Class<?> event, Listener listener) {
    // CopyOnWriteArrayList<Listener> arrayList = holder.get(event);
    // if (CollectionUtils.isNotEmpty(arrayList)) {
    // arrayList.remove(listener);
    // }
    // }

    public static void fireEvent(Event event) {
        CopyOnWriteArrayList<Listener> arrayList = holder.get(event.getClass());
        if (CollectionUtils.isNotEmpty(arrayList)) {
            for (Listener listener : arrayList) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    log.error("Listener Error", e);
                }
            }
        }
    }
}
