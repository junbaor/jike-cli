package com.github.junbaor.jike.util;

import java.util.HashMap;

public class RequestUtils {

    @SuppressWarnings("all")
    public static <K, V> HashMap<K, V> map(Object... args) {
        if (args == null || args.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        HashMap<K, V> m = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            m.put((K) args[i], (V) args[i + 1]);
        }
        return m;
    }

}
