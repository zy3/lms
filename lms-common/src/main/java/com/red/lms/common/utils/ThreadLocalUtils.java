package com.red.lms.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储上下文
 */
public class ThreadLocalUtils {
    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public static void set(String key, Object value) {
        Map<String, Object> cacheMap = context.get();
        cacheMap.put(key, value);
    }

    public static Object get(String key) {
        if (context.get() == null) {
            return null;
        }
        return context.get().get(key);
    }
}
