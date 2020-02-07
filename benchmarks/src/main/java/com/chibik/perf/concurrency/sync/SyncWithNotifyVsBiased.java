package com.chibik.perf.concurrency.sync;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Benchmark)
@BenchmarkMode(AverageTime)
@Measurement(iterations = 5)
public class SyncWithNotifyVsBiased {

    private TestEntity entity;
    private boolean start;

    @Setup(Level.Iteration)
    public void setUp() {
        entity = new TestEntity();
        start = true;
    }

    @Benchmark
    public void notifyAllThenSync() {
        if(start) {
            start = !start;
            synchronized (entity) {
                entity.notifyAll();
            }
        }

        synchronized (entity) {
            entity.s1++;
        }
    }

    @Benchmark
    public void biasedAccess() {
        synchronized (entity) {
            entity.s1++;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SyncWithNotifyVsBiased.class);
    }

    public static class TestEntity {
        public long s1;
        public long s2;
        public long s3;
        public long s4;
    }
}
