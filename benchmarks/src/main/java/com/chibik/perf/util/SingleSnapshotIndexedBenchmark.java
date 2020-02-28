package com.chibik.perf.util;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class SingleSnapshotIndexedBenchmark {

    @Param({"30"})
    protected int randomSeed;

    private int index;

    @Setup(Level.Iteration)
    public void setUpIteration() {
        System.gc();
        System.gc();

        index = 0;
    }

    protected int getIndex() {
        return index;
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }
}
