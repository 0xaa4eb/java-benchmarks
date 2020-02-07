package com.chibik.perf.collections.array;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.IncludedInReport;
import com.chibik.perf.util.SingleSnapshotBenchmark;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleSnapshotBenchmark(batchSize = 1000000, timeUnit = TimeUnit.MILLISECONDS)
public class ArrayIterate {

    public static final int BATCH_SIZE = 1000000;

    @Param({"16", "" + BATCH_SIZE})
    private int initialCapacity;

    private List<Integer> arrayList;

    private List<Integer> linkedList;

    private TIntList tIntList;

    private ByteArrayOutputStream baos;

    @Setup(Level.Iteration)
    public void setUp() {
        arrayList = new ArrayList<>(initialCapacity);
        linkedList = new LinkedList<>();
        tIntList = new TIntArrayList(initialCapacity);
        baos = new ByteArrayOutputStream(initialCapacity);
    }

    @Benchmark
    public boolean arrayList() {
        return arrayList.add(2);
    }

    @Benchmark
    public boolean linkedList() {
        return linkedList.add(2);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ArrayIterate.class);
    }
}