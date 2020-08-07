package com.chibik.perf.latency.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = ReadLatency.BATCH_SIZE)
@Comment("Read cold memory once")
public class ReadLatency extends IndexedLatencyBenchmark {

    public static final int BATCH_SIZE = 1000000;

    public static class StateHolder extends Padder {
        long value;

        StateHolder() {
            value = ThreadLocalRandom.current().nextInt();
        }
    }

    private StateHolder[] arr;

    @Setup(Level.Trial)
    public void setUpTrial() {
        arr = MemUtil.allocateArray(BATCH_SIZE, StateHolder.class, StateHolder::new);
    }

    @Benchmark
    public long read() {
        return arr[getIndex()].value;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ReadLatency.class);
    }
}
