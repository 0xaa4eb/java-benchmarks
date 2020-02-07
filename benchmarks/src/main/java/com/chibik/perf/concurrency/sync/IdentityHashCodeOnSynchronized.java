package com.chibik.perf.concurrency.sync;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Benchmark)
@BenchmarkMode(AverageTime)
@Measurement(iterations = 10)
@Threads(2)
public class IdentityHashCodeOnSynchronized {

    private TestEntity entity;

    @Setup(Level.Iteration)
    public void setUp() {
        entity = new TestEntity();
        entity.s1 = "sagkfhfkf";
        entity.s2 = "54593mfds";
        entity.s3 = "alkjf8923";
        entity.s4 = "fmisdl323";
    }

    @Benchmark
    @Group(value = "sync")
    public int testIdentityHashCodeOnSynchronizedObject() {
        return entity.hashCode();
    }

    @Benchmark
    @Group(value = "sync")
    public void synchronizeObject() throws InterruptedException {
        synchronized (entity) {
            Thread.sleep(500L);
        }
    }

    @Benchmark
    @GroupThreads(value = 1)
    @Group(value = "nonSync")
    public int testHashCodeNonSync() {
        return entity.hashCode();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(IdentityHashCodeOnSynchronized.class);
    }

    public static class TestEntity {
        public String s1;
        public String s2;
        public String s3;
        public String s4;
    }
}
