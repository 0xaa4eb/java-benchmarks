package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.CacheUtil;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.SingleShotBenchmark;
import com.chibik.perf.util.Padder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = ReadLatency.BATCH_SIZE)
@Comment("Read cold memory once")
public class ReadLatency {

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

    @Setup(Level.Iteration)
    public void setUp(Blackhole blackhole) {
        blackhole.consume(CacheUtil.evictCacheLines());

        index = 0;
    }

    @Benchmark
    public long read() {
        return arr[index].value;
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ReadLatency.class);
    }
}
