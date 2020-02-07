package com.chibik.perf.concurrency.other;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Warmup(iterations = 10, batchSize = LongAdderVsAtomicLongVsLocalField.BATCH_SIZE)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5, batchSize = LongAdderVsAtomicLongVsLocalField.BATCH_SIZE)
public class LongAdderVsAtomicLongVsLocalField {

    public static final int BATCH_SIZE = 1000;

    @State(Scope.Thread)
    public static class AtomicLongState {

        @Contended
        public AtomicLong counter = new AtomicLong(0L);

        @Setup(Level.Iteration)
        public void setUp() {
            counter = new AtomicLong(0L);
        }
    }

    @State(Scope.Benchmark)
    public static class LongAdderState {

        @Contended
        public LongAdder longAdder = new LongAdder();

        @Setup(Level.Iteration)
        public void setUp() {
            longAdder = new LongAdder();
        }
    }

    @State(Scope.Thread)
    public static class ThreadLocalState {

        @Contended
        public long counter;

        @Setup(Level.Iteration)
        public void setUp() {
            counter = 0L;
        }
    }

    @Benchmark
    @Group(value = "atomicLong")
    @GroupThreads(value = 5)
    public long testAtomicLong(AtomicLongState atomicLongState) {
        return atomicLongState.counter.incrementAndGet();
    }

    @Benchmark
    @Group(value = "longAdder")
    @GroupThreads(value = 5)
    public void testLongAdder(LongAdderState longAdderState) {
        longAdderState.longAdder.add(1);
    }

    @Benchmark
    @Group(value = "threadLocalField")
    @GroupThreads(value = 5)
    public long testThreadLocal(ThreadLocalState threadLocalState) {
        return threadLocalState.counter++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LongAdderVsAtomicLongVsLocalField.class);
    }
}
