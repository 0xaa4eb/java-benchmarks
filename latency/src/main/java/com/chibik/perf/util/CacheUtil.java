package com.chibik.perf.util;

import java.util.concurrent.ThreadLocalRandom;

public class CacheUtil {

    private static int[] array = new int[8 * 32 * 1024 * 1024];

    static {
        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt();
        }
    }

    public static long evictCacheLines() {
        int sum = 0;
        int xor = 0;
        for (int v : array) {
            sum += v;
            xor ^= v;
        }
        return sum ^ xor;
    }
}
