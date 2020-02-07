package com.chibik.perf.concurrency.queue;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.IncludedInReport;
import org.jctools.queues.SpscArrayQueue;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(batchSize = QueueAddLatency.BATCH_SIZE, iterations = 40)
@Measurement(batchSize = QueueAddLatency.BATCH_SIZE, iterations = 40)
@IncludedInReport
public class QueueAddLatency {

    public static final int BATCH_SIZE = 10000;

    private SpscArrayQueue<Integer> spscArrayQueue;
    private ArrayBlockingQueue<Integer> arrayBlockingQueue;

    @Setup(Level.Iteration)
    public void setUpIteration() {
        if(spscArrayQueue == null) {
            spscArrayQueue = new SpscArrayQueue<>(BATCH_SIZE);
        }
        if(arrayBlockingQueue == null) {
            arrayBlockingQueue = new ArrayBlockingQueue<Integer>(BATCH_SIZE);
        }
        spscArrayQueue.clear();
        arrayBlockingQueue.clear();
    }

    @TearDown(Level.Iteration)
    public void end() {
        if(spscArrayQueue.size() + arrayBlockingQueue.size() == 0) {
            throw new RuntimeException("");
        }
    }

    @Benchmark
    public void offerToSpScArrayQueue() {
        if(!spscArrayQueue.offer(2)) {
            throw new RuntimeException("");
        }
    }

    @Benchmark
    public void offerToAbq() {
        if(!arrayBlockingQueue.offer(2)) {
            throw new RuntimeException("");
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(QueueAddLatency.class);
    }
}