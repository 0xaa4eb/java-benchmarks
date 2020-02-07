package com.chibik.perf.concurrency.queue.juc;

import com.chibik.perf.concurrency.queue.LamportCLFQueue;
import com.chibik.perf.concurrency.queue.MyQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueueConsistencyTest {

    private static final int COUNT_PER_PRODUCER = 1000000;
    private static final int PRODUCERS = 5;
    private static final int CONSUMERS = 4;
    private static final double PRODUCER_RANGE = 10000;

    private static double nextProducerStart = 0;

    public static void main(String[] args) {
        MyQueue<Double> queue = new LamportCLFQueue<>(2 << 10);

        ExecutorService EXECUTOR = Executors.newFixedThreadPool(PRODUCERS + CONSUMERS);
        List<Producer> producers = new ArrayList<>();
        for(int i = 0; i < PRODUCERS; i++) {
            producers.add(buildProducer(queue));
        }
    }

    private static Producer buildProducer(MyQueue<Double> queue) {
        Producer producer = new Producer(
                queue,
                nextProducerStart,
                nextProducerStart + PRODUCER_RANGE,
                COUNT_PER_PRODUCER
        );
        nextProducerStart += PRODUCER_RANGE;
        return producer;
    }

    private static class Producer implements Runnable {

        private MyQueue<Double> queue;
        private double start;
        private int count;
        private double delta;

        public Producer(MyQueue<Double> queue, double start, double end, int count) {
            this.queue = queue;
            this.start = start;
            this.count = count;
            delta = (end - start - 1)/count;
        }

        @Override
        public void run() {

            double nextVal = start;
            for(int i = 0; i < count; i++) {
                while(!queue.enqueue(nextVal));
                nextVal += delta;
            }
        }
    }

    private static class Consumer implements Runnable {
        private MyQueue<Double> queue;

        @Override
        public void run() {
            int count = 0;
            for(;;) {

            }
        }
    }
}
