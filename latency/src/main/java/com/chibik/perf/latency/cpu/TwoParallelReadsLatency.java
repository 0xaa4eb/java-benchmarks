package com.chibik.perf.latency.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = TwoParallelReadsLatency.BATCH_SIZE)
@Comment("Read two not dependent addresses, CPU should do this in parallel")
public class TwoParallelReadsLatency extends IndexedLatencyBenchmark {

    public static final int BATCH_SIZE = 1000000;

    public static class StateHolder extends Padder {
        long value;

        StateHolder() {
            value = getHeadXored();
        }
    }

    private StateHolder[] arr;
    private StateHolder[] arr2;

    @Setup(Level.Trial)
    public void setUpTrial() {
        arr = MemUtil.allocateArray(BATCH_SIZE, StateHolder.class, StateHolder::new);
        arr2 = MemUtil.allocateArray(BATCH_SIZE, StateHolder.class, StateHolder::new);
    }

    @Benchmark
    public long read() {
        long v1 = arr[getIndex()].value;
        long v2 = arr2[getIndex()].value;
        return v1 + v2;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TwoParallelReadsLatency.class);
    }
}
