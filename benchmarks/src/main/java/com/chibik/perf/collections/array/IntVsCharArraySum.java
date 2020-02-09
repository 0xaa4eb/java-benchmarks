package com.chibik.perf.collections.array;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@AvgTimeBenchmark
@State(Scope.Thread)
public class IntVsCharArraySum {

    @Param({"32768"})
    int size;

    private int[] intArray;

    private char[] charArray;

    @Setup(Level.Trial)
    public void setUp() {
        intArray = new int[size];
        charArray = new char[size];
        for (int i = 0; i < size; i++) {
            intArray[i] = i ^ 255;
            charArray[i] = (char) (i ^ 255);
        }
    }

    @Benchmark
    public int intArray() {
        int r = 0;
        for (int x = 0; x < intArray.length; x++) {
            r ^= intArray[x];
        }
        return r;
    }

    @Benchmark
    public int charArrayXorToInt() {
        int r = 0;
        for (int x = 0; x < charArray.length; x++) {
            r ^= charArray[x];
        }
        return r;
    }

    @Benchmark
    public char charArrayXorToChar() {
        char r = 0;
        for (int x = 0; x < charArray.length; x++) {
            r ^= charArray[x];
        }
        return r;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(IntVsCharArraySum.class);
    }
}
