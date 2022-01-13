package com.chibik.perf.asm.autovec;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AdditionalForkJvmKeys;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@PrintAssembly
public class AutoVecExample {

    private int[] left;
    private int[] right;
    private int[] sumResult;

    @Setup(Level.Iteration)
    public void setUp() {
        int size = 4 * 1024;
        left = new int[size];
        right = new int[size];
        sumResult = new int[size];
        for (int i = 0; i < size; i++) {
            left[i] = ThreadLocalRandom.current().nextInt();
            right[i] = ThreadLocalRandom.current().nextInt();
        }
    }

    @Benchmark
    public int[] autovecEnabled() {
        for (int i = 0; i < left.length; i++) {
            sumResult[i] = left[i] + right[i];
        }
        return sumResult;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(AutoVecExample.class);
    }
}
