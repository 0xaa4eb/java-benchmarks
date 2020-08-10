package com.chibik.perf.latency.collections.list;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// TODO fix this benchmark
@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = ArrayListVsLinkedListIteratingLatency.BATCH_SIZE)
@Comment("Compute sum of 32 elements list in different List implementations")
public class ArrayListVsLinkedListIteratingLatency extends IndexedLatencyBenchmark {

    public static final int BATCH_SIZE = 100000;

    private static final class Int {

        private final int value;

        private Int(int value) {
            this.value = value;
        }
    }

    private static class StateHolder extends Padder {
        final List<Int> list;

        private StateHolder(List<Int> list) {
            this.list = list;
        }

        public int sum() {
            int sum = 0;
            for (Int i : list) {
                sum = (sum + i.value ^ sum);
            }
            return sum;
        }
    }

    @Param({"ArrayList", "LinkedList"})
    private String listImpl;

    private StateHolder[] arr;

    private List<Int> initList(List<Int> list, List<Int> pool) {
        for (int i = 0; i < 32; i++) {
            list.add(pool.remove(pool.size() - 1));
        }
        return list;
    }

    @Setup(Level.Trial)
    public void setUpTrial() {
        List<Int> pool = new ArrayList<>();
        for (int i = 0; i < 32 * BATCH_SIZE; i++) {
            pool.add(new Int(ThreadLocalRandom.current().nextInt()));
        }
        Collections.shuffle(pool);

        if (listImpl.equals("ArrayList")) {
            arr = MemUtil.allocateArray(BATCH_SIZE, StateHolder.class, () -> new StateHolder(initList(new ArrayList<>(), pool)));
        } else {
            arr = MemUtil.allocateArray(BATCH_SIZE, StateHolder.class, () -> new StateHolder(initList(new LinkedList<>(), pool)));
        }
        if (!pool.isEmpty()) {
            throw new RuntimeException(":asd");
        }
    }

    @Benchmark
    public void iterate(Blackhole blackhole) {
        blackhole.consume(arr[getIndex()].sum());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ArrayListVsLinkedListIteratingLatency.class);
    }
}
