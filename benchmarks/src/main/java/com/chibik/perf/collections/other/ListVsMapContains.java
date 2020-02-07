package com.chibik.perf.collections.other;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import com.chibik.perf.util.SingleSnapshotBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleSnapshotBenchmark(batchSize = ListVsMapContains.BATCH_SIZE)
@Comment("ArrayList vs HashSet contains(..) on small sizes. " +
        ListVsMapContains.BATCH_SIZE + " values to check contains(...) method, result is always true")
public class ListVsMapContains {

    public static final int BATCH_SIZE = 1000000;

    @Param({"2", "4", "8", "16", "32", "64", "256"})
    private int size;

    private String[] valueToPoll = new String[BATCH_SIZE];

    private ArrayList<String> list;

    private HashSet<String> set;

    private int index;

    @Setup(Level.Iteration)
    public void init() {
        list = new ArrayList<>(size);
        set = new HashSet<>((int) (size / 0.75));

        Random r = new Random(30);
        List<String> values = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            int v = 100000 + r.nextInt(10000);
            String value = "" + v;
            values.add(value);
            list.add(value);
        }

        for(int i = 0; i < BATCH_SIZE; i++) {
            valueToPoll[i] = values.get(r.nextInt(size));
        }

        index = 0;
    }

    @Benchmark
    public boolean arrayList() {
        return list.contains(valueToPoll[index]);
    }

    @Benchmark
    public boolean hashSet() {
        return set.contains(valueToPoll[index]);
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ListVsMapContains.class);
    }
}
