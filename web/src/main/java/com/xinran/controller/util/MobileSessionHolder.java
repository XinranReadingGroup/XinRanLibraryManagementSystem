package com.xinran.controller.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 高海军 帝奇 Jul 25, 2015 1:16:44 PM
 */
public abstract class MobileSessionHolder {

    // 可以考虑未来放在cache之中,并持久化之.
    private static Map<String, Long> map = new ConcurrentHashMap<String, Long>(1024);

    public  static void attachUserIdToAccessToken(String accessToken, Long userId) {
        map.put(accessToken, userId);
    }

    public static Long getCurrentUserIdByAccessToken(String accessToken) {
        return map.get(accessToken);
    }
}
