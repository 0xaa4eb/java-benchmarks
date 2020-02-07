package com.chibik.perf.concurrency.queue;

public interface MyQueue<T> {

    boolean enqueue(T data);

    boolean dequeue(DataHolder<T> holder);
}
