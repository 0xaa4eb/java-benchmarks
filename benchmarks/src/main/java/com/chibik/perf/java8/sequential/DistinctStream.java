package com.chibik.perf.java8.sequential;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@IncludedInReport
public class DistinctStream {

    @Param({"5", "50", "500", "5000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i));
        }

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("set = new HashSet<>(entities); return new ArrayList<>(set)")
    public Object manual() {
        Set<StreamEntity> set = new HashSet<>(entities);

        return new ArrayList<>(set);
    }

    @Benchmark
    @Comment("stream().distinct().collect(toList());")
    public Object stream() {
        return entities.stream().distinct().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(DistinctStream.class);
    }
}
