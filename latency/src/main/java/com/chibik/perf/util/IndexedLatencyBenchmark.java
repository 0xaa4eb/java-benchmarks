package com.chibik.perf.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class IndexedLatencyBenchmark extends LatencyBenchmark {

    protected int index;

    protected final int getIndex() {
        return index;
    }

    @Setup(Level.Iteration)
    public final void setUpIndex(Blackhole blackhole) {
        System.gc();
        System.gc();

        index = 0;
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }
}
