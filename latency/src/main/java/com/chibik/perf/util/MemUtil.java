package com.chibik.perf.util;

import java.lang.reflect.Array;
import java.util.function.Supplier;

public class MemUtil {

    @SuppressWarnings("unchecked")
    public static <T> T[] val(int size, Class<?> type, Supplier<T> supplier) {
        try {
            T[] arr = (T[]) Array.newInstance(type, size);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = supplier.get();
            }
            return (T[]) arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
