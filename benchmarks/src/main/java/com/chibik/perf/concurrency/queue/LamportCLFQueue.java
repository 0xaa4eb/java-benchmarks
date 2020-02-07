package com.chibik.perf.concurrency.queue;

import util.Contended;

public class LamportCLFQueue<T> extends AbstractMyQueue<T> {

    @Contended
    private volatile int head;
    @Contended
    private volatile int tail;

    @Contended
    private final T[] buffer;

    public LamportCLFQueue(int length) {
        super(length);
        this.buffer = (T[]) new Object[length];
        head = tail = 0;
    }

    public boolean enqueue(T data) {
        int tailCached = tail;
        if(head == next(tailCached)) {
            return false;
        }

        buffer[tailCached] = data;
        tail = next(tailCached);

        return true;
    }

    public boolean dequeue(DataHolder<T> holder) {
        int headCached = head;
        if(headCached == tail) {
            return false;
        }

        holder.entry = buffer[head];
        head = next(head);

        return true;
    }

    private int next(int p) {
        return (p + 1) & mask;
    }
}
