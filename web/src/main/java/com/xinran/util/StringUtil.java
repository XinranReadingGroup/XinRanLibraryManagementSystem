package com.xinran.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:00:29 PM
 */
public abstract class StringUtil {

    public static String random(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
        }
        return sb.toString();
    }
}
