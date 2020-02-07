package com.chibik.perf.concurrency.sync;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Control;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Benchmark)
@BenchmarkMode(AverageTime)
@Measurement(iterations = 5)
@Comment("long++ inside critical section using synchronized")
public class SyncInflatedVsThinLock {

    /*WARNING: VERY ROUGH TEST*/

    private TestEntity entity;
    private boolean start;
    private AtomicBoolean flag;

    @Setup(Level.Iteration)
    public void setUp() {
        entity = new TestEntity();
        start = true;
        flag = new AtomicBoolean(false);
    }

    @Benchmark
    @Group("thinlock")
    @GroupThreads(value = 1)
    public void sync1(Control cnt) {
        while (!cnt.stopMeasurement && !flag.compareAndSet(false, true)) {
            synchronized (entity) {
                entity.s1++;
            }
        }
    }

    @Benchmark
    @Group("thinlock")
    @GroupThreads(value = 1)
    public void sync2(Control cnt) {
        while (!cnt.stopMeasurement && !flag.compareAndSet(true, false)) {
            synchronized (entity) {
                entity.s1++;
            }
        }
    }

    @Benchmark
    @Group("fatlock")
    @GroupThreads(value = 1)
    public void sync1Fat(Control cnt) {
        if(start) {
            //can be called two times, we don't care
            start = false;
            synchronized (entity) {
                entity.notifyAll();
            }
        }

        while (!cnt.stopMeasurement && !flag.compareAndSet(false, true)) {
            synchronized (entity) {
                entity.s1++;
            }
        }
    }

    @Benchmark
    @Group("fatlock")
    @GroupThreads(value = 1)
    public void sync2Fat(Control cnt) {
        if(start) {
            //can be called two times, we don't care
            start = false;
            synchronized (entity) {
                entity.notifyAll();
            }
        }

        while (!cnt.stopMeasurement && !flag.compareAndSet(true, false)) {
            synchronized (entity) {
                entity.s1++;
            }
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SyncInflatedVsThinLock.class);
    }

    public static class TestEntity {
        public long s1;
        public long s2;
        public long s3;
        public long s4;
    }

}
