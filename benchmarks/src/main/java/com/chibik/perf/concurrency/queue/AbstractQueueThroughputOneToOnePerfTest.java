package com.chibik.perf.concurrency.queue;

import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, batchSize = AbstractQueueThroughputOneToOnePerfTest.BATCH_SIZE)
@Measurement(iterations = 10, batchSize = AbstractQueueThroughputOneToOnePerfTest.BATCH_SIZE)
@BenchmarkMode(Mode.SingleShotTime)
public abstract class AbstractQueueThroughputOneToOnePerfTest {

    public static final int BATCH_SIZE = 10_000_000;

    public abstract Object recreateQueue();

    public abstract void addImpl(int x) throws InterruptedException;

    public abstract int getImpl() throws InterruptedException;

    @Contended
    private int item;

    @Contended
    private int result;

    private Object queue;
    private long lastPrintTime = -1;

    @Setup(Level.Iteration)
    public void setUp() {
        item = 0;
        result = 0;
        queue = recreateQueue();

        System.gc();
        System.gc();
        System.gc();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        int expected = 0;
        for(int i = 1; i < BATCH_SIZE + 1; i++) {
            expected += 1;
        }

        if(result != expected) {
            throw new RuntimeException("Was " + result + ", expected " + expected);
        }
    }

    @GroupThreads(value = 1)
    @Group("queuetest")
    @Benchmark
    public void put() throws InterruptedException {

        addImpl(1);
    }

    @GroupThreads(value = 1)
    @Group("queuetest")
    @Benchmark
    public int get() throws InterruptedException {

        result += getImpl();
        return result;
    }

    @GroupThreads(value = 1)
    @Group("queuetest")
    @Benchmark
    public int print() throws InterruptedException {
        if(System.currentTimeMillis() - lastPrintTime > 200) {
            lastPrintTime = System.currentTimeMillis();
//            System.out.println(queue);
        }
        return 0;
    }
}
