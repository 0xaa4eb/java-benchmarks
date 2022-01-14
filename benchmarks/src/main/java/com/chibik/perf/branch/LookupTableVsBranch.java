package com.chibik.perf.branch;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark
@State(Scope.Thread)
@Comment("Initialize table as float[]{1f, 2f} and return one of this values depending " +
        "on the random value. Random value can be 0 or 1")
public class LookupTableVsBranch {

    private ThreadLocalRandom rand = ThreadLocalRandom.current();

    private final float[] table = new float[]{1f, 2f};

    @Benchmark
    @Comment("Simple if/else branch")
    public float int01_testBranch() {
        int r = rand.nextInt(2);
        if (r == 0) {
            return 1f;
        } else {
            return 2f;
        }
    }

    @Benchmark
    @Comment("return table[rand]")
    public float int01_testLookup() {
        int r = rand.nextInt(2);
        return table[r];
    }

    @Benchmark
    @Comment("return table[rand]")
    public float boolean_testBranch() {
        boolean r = rand.nextBoolean();
        if (r) {
            return 1f;
        } else {
            return 2f;
        }
    }

    @Benchmark
    @Comment("return table[r ? 1 : 0]")
    public float boolean_testLookup2() {
        boolean r = rand.nextBoolean();
        return table[r ? 1 : 0];
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(LookupTableVsBranch.class);
    }
}
