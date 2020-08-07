package com.chibik.perf.util;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class MemUtil {

    @SuppressWarnings("unchecked")
    public static <T> T[] allocateArray(int size, Class<?> type, Supplier<T> supplier) {
        try {
            T[] arr = (T[]) Array.newInstance(type, size);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = supplier.get();
            }

            shuffleArray(arr);
            return (T[]) arr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> void shuffleArray(T[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            T a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
