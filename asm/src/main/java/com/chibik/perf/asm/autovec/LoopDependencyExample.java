package com.chibik.perf.asm.autovec;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@PrintAssembly
public class LoopDependencyExample {

    private int[] left;
    private int[] right;
    private int[] sumResult;

    @Setup(Level.Iteration)
    public void setUp() {
        int size = 32 * 1024;
        left = new int[size];
        right = new int[size];
        sumResult = new int[size];
        for (int i = 0; i < size; i++) {
            left[i] = ThreadLocalRandom.current().nextInt();
            right[i] = ThreadLocalRandom.current().nextInt();
        }
    }

    @Benchmark
    public int[] sum() {
        int[] left = this.left;
        int leng = left.length;
        int[] right = this.right;
        int[] sumResult = this.sumResult;

        for (int i = 1; i < leng; i++) {
            sumResult[i] = left[i] + right[i];
        }
        return sumResult;
    }

    @Benchmark
    public int[] dependency() {
        int[] left = this.left;
        int leng = left.length;
        int[] sumResult = this.sumResult;

        for (int i = 1; i < leng; i++) {
            sumResult[i] = sumResult[i - 1] + left[i];
        }
        return sumResult;
    }

    // LoopDependencyExample.dependency                      avgt    5  11924.115 ± 539.257   ns/op
    // LoopDependencyExample.sum                             avgt    5   6506.965 ± 109.077   ns/op

    public static void main(String[] args) {
        BenchmarkRunner.run(LoopDependencyExample.class);
    }
}
