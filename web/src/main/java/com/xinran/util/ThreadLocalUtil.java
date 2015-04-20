package com.xinran.util;

/**
 * @author 高海军 帝奇 Apr 19, 2015 11:43:33 AM
 */
public abstract class ThreadLocalUtil {

    private static final ThreadLocal<Object> xx = new ThreadLocal<Object>();

    public static void set(Object value) {
        xx.set(value);
    }

    public static void remove(Object value) {
        xx.remove();
    }

    public static Object get() {
        return xx.get();
    }

}
