package com.chibik.perf.branch;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.PerfCounterProfiled;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark(iterations = 5, warmupIterations = 5)
@State(Scope.Thread)
@Fork(jvmArgs = "-XX:ConditionalMoveLimit=0")
@PerfCounterProfiled("branches,branch-misses")
public class BranchMisspredictExample {

    public static final int SIZE = 4 * 1024;

    private int[] everyBranchTable;
    private int[] halfRandomTable;
    private int[] everyOddBranchTable;

    private int index = 0;

    @Setup(Level.Iteration)
    public void setUp() {
        everyBranchTable = new int[SIZE];
        everyOddBranchTable = new int[SIZE];
        halfRandomTable = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            everyBranchTable[i] = 1;
            everyOddBranchTable[i] = i % 2 == 0 ? 1 : 0;
            halfRandomTable[i] = ThreadLocalRandom.current().nextBoolean() ? 1 : 0;
        }
    }

    @Benchmark
    public int everyBranchIsSame() {
        int next = index++ & (SIZE - 1);
        if (everyBranchTable[next] > 0) {
            return 555;
        } else {
            return 5;
        }
    }

    @Benchmark
    public int everyOddBranchTaken() {
        int next = index++ & (SIZE - 1);
        if (everyOddBranchTable[next] > 0) {
            return 555;
        } else {
            return 5;
        }
    }

    @Benchmark
    public int halfRandomBranch() {
        int next = index++ & (SIZE - 1);
        if (halfRandomTable[next] > 0) {
            return 555;
        } else {
            return 5;
        }
    }

    /*
    everyBranchIsSame:branch-misses          avgt        0.001             #/op
    everyBranchIsSame:branches               avgt        7.011             #/op
    everyOddBranchTaken:branch-misses        avgt        0.001             #/op
    everyOddBranchTaken:branches             avgt        7.506             #/op
    halfRandomBranch:branch-misses           avgt        0.335             #/op
    halfRandomBranch:branches                avgt        7.525             #/op
    */

    public static void main(String[] args) {
        BenchmarkRunner.run(BranchMisspredictExample.class);
    }
}
