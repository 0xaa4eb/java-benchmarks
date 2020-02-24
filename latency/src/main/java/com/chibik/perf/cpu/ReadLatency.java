package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

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

    private final StateHolder[] arr = new StateHolder[BATCH_SIZE];
    private int index;

    @Setup(Level.Trial)
    public void setUpTrial() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new StateHolder();
            arr[i].value = arr[i].getHeadXored();
        }
    }

    @Benchmark
    public long read() {
        return arr[index].value;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ReadLatency.class);
    }
}
