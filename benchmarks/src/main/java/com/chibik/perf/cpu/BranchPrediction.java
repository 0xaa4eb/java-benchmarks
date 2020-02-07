package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@AvgTimeBenchmark
@Comment("Preallocate array of values (1 or 0) and iterate it. If entry == 1 then sum is incremented. " +
        "Otherwise it is decremented")
@IncludedInReport
public class BranchPrediction {

    int size = 32768;

    @Contended
    private int[] constant = new int[size];

    @Contended
    private int[] every5 = new int[size];

    @Contended
    private int[] every10 = new int[size];

    @Contended
    private int[] rand = new int[size];

    @Setup(Level.Iteration)
    public void setUp() {
        for(int i = 0; i < size; i++) {
            constant[i] = (i % 1000 == 0) ? 1 : 0;

            every5[i] = (i % 5 == 0) ? 1 : 0;

            every10[i] = (i % 10 == 0) ? 1 : 0;

            rand[i] = ThreadLocalRandom.current().nextInt(2);
        }
    }

    @Benchmark
    @Comment("Every 1000 entry == 1")
    public int constantBranch() {
        int sum = 0;
        for(int i = 0; i < size; i++) {
            if(constant[i] == 0) {
                sum++;
            } else {
                sum--;
            }
        }
        return sum;
    }

    @Benchmark
    @Comment("Every 5 entry == 1")
    public int every5Branch() {
        int sum = 0;
        for(int i = 0; i < size; i++) {
            if(every5[i] == 0) {
                sum++;
            } else {
                sum--;
            }
        }
        return sum;
    }

    @Comment("Every 10 entry == 1")
    @Benchmark
    public int every10Branch() {
        int sum = 0;
        for(int i = 0; i < size; i++) {
            if(every10[i] == 0) {
                sum++;
            } else {
                sum--;
            }
        }
        return sum;
    }

    @Benchmark
    @Comment("Each entry is chosen randomly == 1")
    public int randBranch() {
        int sum = 0;
        for(int i = 0; i < size; i++) {
            if(rand[i] == 0) {
                sum++;
            } else {
                sum--;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(BranchPrediction.class);
    }
}
