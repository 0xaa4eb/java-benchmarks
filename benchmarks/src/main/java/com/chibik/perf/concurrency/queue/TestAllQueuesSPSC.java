package com.chibik.perf.concurrency.queue;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.Param;

import java.lang.reflect.Constructor;

public class TestAllQueuesSPSC extends AbstractQueueThroughputOneToOnePerfTest {

    @Param({
  /*      "com.chibik.perf.concurrency.queue.LamportCLFQueue",
        "com.chibik.perf.concurrency.queue.LamportCLFQueuePutOrdered",
            "com.chibik.perf.concurrency.queue.SpscAtomicQueueWrapper",*/
        "com.chibik.perf.concurrency.queue.FastForwardQueue",
    })
    private String className;

    private MyQueue<Integer> queue;

    @Param({"131072"})
    private int len;

    @Override
    public MyQueue recreateQueue() {
        try {
            Constructor c = Class.forName(className).getConstructors()[0];

            queue = (MyQueue<Integer>) c.newInstance(len);
            return queue;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addImpl(int x) throws InterruptedException {

        while(!queue.enqueue(x));
    }

    @Override
    public int getImpl() throws InterruptedException {

        DataHolder<Integer> data = new DataHolder<>();
        while(!queue.dequeue(data));
        return data.entry;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(TestAllQueuesSPSC.class);
    }
}
