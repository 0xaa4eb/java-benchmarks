package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.SingleShotBenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = MemReadLatency.BATCH_SIZE, timeUnit = TimeUnit.MICROSECONDS, iterations = 50)
@Comment("Initialize array of a random numbers and put all them to the map. " +
        "Test measures get() method time. 1 is used as a value")
public class MemReadLatency {

    public static final int BATCH_SIZE = 10000;

    public static abstract class StateHolderHead {
        long head1;
        long head2;
        long head3;
        long head4;
        long head5;
        long head6;
        long head7;
        long head8;
        long head9;
        long head10;

        StateHolderHead() {
            head1 = ThreadLocalRandom.current().nextInt();
            head2 = ThreadLocalRandom.current().nextInt();
            head3 = ThreadLocalRandom.current().nextInt();
            head4 = ThreadLocalRandom.current().nextInt();
            head5 = ThreadLocalRandom.current().nextInt();
            head6 = ThreadLocalRandom.current().nextInt();
            head7 = ThreadLocalRandom.current().nextInt();
            head8 = ThreadLocalRandom.current().nextInt();
            head9 = ThreadLocalRandom.current().nextInt();
            head10 = ThreadLocalRandom.current().nextInt();
        }

        public long getHeadXored() {
            return head1 ^ head2 ^ head3 ^ head4 ^ head5 ^ head6 ^ head7 ^ head8 ^ head9 ^ head10;
        }
    }

    public static abstract class StateHolder extends StateHolderHead {
        long mem;

        StateHolder() {
            mem = ThreadLocalRandom.current().nextInt();
        }
    }

    public static class StateHolderTail extends StateHolder {
        long tail1;
        long tail2;
        long tail3;
        long tail4;
        long tail5;
        long tail6;
        long tail7;
        long tail8;
        long tail9;
        long tail10;

        StateHolderTail() {
            tail1 = ThreadLocalRandom.current().nextInt();
            tail2 = ThreadLocalRandom.current().nextInt();
            tail3 = ThreadLocalRandom.current().nextInt();
            tail4 = ThreadLocalRandom.current().nextInt();
            tail5 = ThreadLocalRandom.current().nextInt();
            tail6 = ThreadLocalRandom.current().nextInt();
            tail7 = ThreadLocalRandom.current().nextInt();
            tail8 = ThreadLocalRandom.current().nextInt();
            tail9 = ThreadLocalRandom.current().nextInt();
            tail10 = ThreadLocalRandom.current().nextInt();
        }
        
        public long getTailXored() {
            return tail1 ^ tail2 ^ tail3 ^ tail4 ^ tail5 ^ tail6 ^ tail7 ^ tail8 ^ tail9 ^ tail10;
        }
    }

    private final StateHolderTail[] arr = new StateHolderTail[BATCH_SIZE];
    private int index;

    @Setup(Level.Trial)
    public void setUpTrial() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new StateHolderTail();
            arr[i].mem = arr[i].getTailXored() ^ arr[i].getHeadXored();
        }
    }

    @Setup(Level.Iteration)
    public void setUp(Blackhole blackhole) {
//        blackhole.consume(CacheUtil.evictCacheLines());

        index = 0;
    }

    @Benchmark
    public long test() {
        return arr[index].mem;
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(MemReadLatency.class);
    }
}
