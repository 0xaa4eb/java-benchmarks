package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = TwoParallelReadsLatency.BATCH_SIZE)
@Comment("Read two not dependent addresses, CPU should do this in parallel")
public class TwoParallelReadsLatency {

    public static final int BATCH_SIZE = 1000000;

    public static class StateHolder extends Padder {
        long value;

        StateHolder() {
            value = getHeadXored();
        }
    }

    private StateHolder[] arr;
    private StateHolder[] arr2;
    private int index;

    @Setup(Level.Trial)
    public void setUpTrial() {
        arr = MemUtil.val(BATCH_SIZE, StateHolder.class, StateHolder::new);
        arr2 = MemUtil.val(BATCH_SIZE, StateHolder.class, StateHolder::new);
    }

    @Setup(Level.Iteration)
    public void setUp(Blackhole blackhole) {
        blackhole.consume(CacheUtil.evictCacheLines());
        index = 0;
    }

    @Benchmark
    public long read() {
        long v1 = arr[index].value;
        long v2 = arr2[index].value;
        return v1 + v2;
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TwoParallelReadsLatency.class);
    }
}
