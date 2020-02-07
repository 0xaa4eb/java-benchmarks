package com.chibik.perf.concurrency.queue.juc;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.concurrency.queue.AbstractQueueThroughputOneToOnePerfTest;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest extends AbstractQueueThroughputOneToOnePerfTest {

    private ConcurrentLinkedQueue<Integer> queue;

    @Override
    public Object recreateQueue() {
        queue = new ConcurrentLinkedQueue<>();
        return queue;
    }

    @Override
    public void addImpl(int x) throws InterruptedException {
        queue.add(x);
    }

    @Override
    public int getImpl() throws InterruptedException {
        Integer val;
        while((val = queue.poll()) == null) {}
        return val;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ConcurrentLinkedQueueTest.class);
    }
}
