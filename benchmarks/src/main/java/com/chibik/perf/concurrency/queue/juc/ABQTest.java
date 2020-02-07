package com.chibik.perf.concurrency.queue.juc;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.concurrency.queue.AbstractQueueThroughputOneToOnePerfTest;

import java.util.concurrent.ArrayBlockingQueue;

public class ABQTest extends AbstractQueueThroughputOneToOnePerfTest {

    private ArrayBlockingQueue<Integer> queue;

    @Override
    public Object recreateQueue() {
        queue = new ArrayBlockingQueue<>(1024);
        return queue;
    }

    @Override
    public void addImpl(int x) throws InterruptedException {
        queue.put(x);
    }

    @Override
    public int getImpl() throws InterruptedException {
        return queue.take();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ABQTest.class);
    }
}
