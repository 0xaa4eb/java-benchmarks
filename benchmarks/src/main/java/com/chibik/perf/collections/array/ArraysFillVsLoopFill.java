package com.chibik.perf.collections.array;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;

@State(Scope.Thread)
@AvgTimeBenchmark
public class ArraysFillVsLoopFill {

    @Param({"1", "10", "100", "1000"})
    private int length;

    private int[] intArray;

    @Setup(Level.Iteration)
    public void setUp() {
        intArray = new int[length];
        for (int i = 0; i < length; i++) {
            intArray[i] = i % 751;
        }
    }

    @Benchmark
    public void arraysFill() {
        Arrays.fill(intArray, 0xdeadbeef);
    }

    @Benchmark
    public void loopFill() {
        for (int i = 0; i < length; i++) {
            intArray[i] = 0xdeadbeef;
        }
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(ArraysFillVsLoopFill.class);
    }
}
