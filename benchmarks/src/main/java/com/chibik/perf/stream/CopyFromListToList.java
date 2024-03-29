package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AvgTimeBenchmark
@State(Scope.Thread)
public class CopyFromListToList {

    @Param({"5", "50", "500", "5000", "50000", "500000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i));
        }

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("return new ArrayList<>(entities)")
    public Object manual() {

        return new ArrayList<>(entities);
    }

    @Benchmark
    @Comment("parallelStream().collect(toList());")
    public Object streamParallel() {
        return entities.parallelStream().collect(Collectors.toList());
    }

    @Benchmark
    @Comment("stream().collect(toList());")
    public Object stream() {
        return entities.stream().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CopyFromListToList.class);
    }
}
