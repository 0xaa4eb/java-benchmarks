package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = TwoDependentReadsLatency.BATCH_SIZE)
@Comment("Read two dependent addresses. This may not be done in parallel")
public class TwoDependentReadsLatency {

    public static final int BATCH_SIZE = 1000000;

    private static class Link extends Padder {
        public final long value;

        public Link(long value) {
            this.value = value;
        }
    }

    private static class StateHolder extends Padder {
        Link link;

        StateHolder() {
            link = new Link(getHeadXored());
        }

        public Link getLink() {
            return link;
        }
    }

    private StateHolder[] arr;
    private int index;

    @Setup(Level.Trial)
    public void setUpTrial() {
        arr = MemUtil.val(BATCH_SIZE, StateHolder.class, StateHolder::new);
    }

    @Setup(Level.Iteration)
    public void setUp(Blackhole blackhole) {
        blackhole.consume(CacheUtil.evictCacheLines());
        index = 0;
    }

    @Benchmark
    public void read(Blackhole blackhole) {
        Link link = arr[index].getLink();
        blackhole.consume(link.value);
        blackhole.consume(link);
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TwoDependentReadsLatency.class);
    }
}
