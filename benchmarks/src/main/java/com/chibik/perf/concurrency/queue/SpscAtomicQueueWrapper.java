package com.chibik.perf.concurrency.queue;

import org.jctools.queues.atomic.SpscAtomicArrayQueue;

public class SpscAtomicQueueWrapper<T> extends AbstractMyQueue<T> {

    private SpscAtomicArrayQueue<T> queue;

    public SpscAtomicQueueWrapper(int length) {
        super(length);
        queue = new SpscAtomicArrayQueue<>(length);
    }

    @Override
    public boolean enqueue(T data) {
        return queue.offer(data);
    }

    @Override
    public boolean dequeue(DataHolder holder) {
        T d;
        while((d = queue.poll()) == null);
        holder.entry = d;
        return true;
    }
}
