package com.chibik.perf.util;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
public class LatencyBenchmark {

    @Setup(Level.Iteration)
    public final void setUpLatency(Blackhole blackhole) {
        blackhole.consume(CacheUtil.evictCacheLines());

        System.gc();
        System.gc();
    }
}
