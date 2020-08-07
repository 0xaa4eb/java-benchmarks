package com.chibik.perf.latency.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IndexedLatencyBenchmark;
import com.chibik.perf.util.Padder;
import com.chibik.perf.util.SingleShotBenchmark;
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

    private final StateHolder[] arr = new StateHolder[BATCH_SIZE];

    @Setup(Level.Trial)
    public void setUpTrial() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new StateHolder();
            arr[i].value = arr[i].getHeadXored();
        }
    }

    @Benchmark
    public long read() {
        return arr[getIndex()].value;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ReadLatency.class);
    }
}
