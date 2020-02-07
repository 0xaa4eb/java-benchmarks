package com.chibik.perf.java;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class IntVsCharArraySum {

    @Param({ "32768" })
    int size;

    @Contended
    private int[] intArray;

    private char[] charArray;

    @Setup(Level.Trial)
    public void setUp() {
        intArray = new int[size];
        charArray = new char[size];
        for(int i = 0; i < size; i++) {
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
