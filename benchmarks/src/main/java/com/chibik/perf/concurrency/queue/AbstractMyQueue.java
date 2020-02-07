package com.chibik.perf.concurrency.queue;

public abstract class AbstractMyQueue<T> implements MyQueue<T> {

    protected final int length;

    protected final int mask;

    public AbstractMyQueue(int length) {
        if(length <= 0 || (length & (length - 1)) != 0) {
            throw new RuntimeException("Invalid length " + length + ", should be positive and power of 2");
        }

        this.length = length;
        this.mask = length - 1;
    }
}
