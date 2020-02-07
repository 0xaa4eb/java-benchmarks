package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.TimeUnit;

@AvgTimeBenchmark
@State(Scope.Thread)
@Comment("Preallocate array and calculate its xor. In some benchmarks loop is unrolled")
public class LoopUnrollingXor {

    @Param({ "32768" })
    int size;

    @Contended
    private int[] intArray;

    private int res;

    @Setup(Level.Iteration)
    public void setUp() {
        intArray = new int[size];
        for(int i = 0; i < size; i++) {
            intArray[i] = i % 751;
        }
        res = 0;
    }

    @TearDown(Level.Iteration)
    public void validate() {
        if(res != 820) {
            throw new RuntimeException("" + res);
        }
    }

    @Benchmark
    public int unrolled() {
        int r = 0;
        for (int x = 0; x < intArray.length; x++) {
            r ^= intArray[x];
        }
        res = r;
        return res;
    }

    @Benchmark
    public int unrolled4() {
        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        int r4 = 0;

        for (int x = 0; x < intArray.length; x+=4) {
            r1 ^= intArray[x];
            r2 ^= intArray[x + 1];
            r3 ^= intArray[x + 2];
            r4 ^= intArray[x + 3];
        }

        res = r1 ^ r2 ^ r3 ^ r4;
        return res;
    }

    @Benchmark
    public int unrolled8() {
        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        int r4 = 0;
        int r5 = 0;
        int r6 = 0;
        int r7 = 0;
        int r8 = 0;

        for (int x = 0; x < intArray.length; x+=8) {
            r1 ^= intArray[x];
            r2 ^= intArray[x + 1];
            r3 ^= intArray[x + 2];
            r4 ^= intArray[x + 3];
            r5 ^= intArray[x + 4];
            r6 ^= intArray[x + 5];
            r7 ^= intArray[x + 6];
            r8 ^= intArray[x + 7];
        }

        res = r1 ^ r2 ^ r3 ^ r4 ^ r5 ^ r6 ^ r7 ^ r8;
        return res;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(LoopUnrollingXor.class);
    }
}
