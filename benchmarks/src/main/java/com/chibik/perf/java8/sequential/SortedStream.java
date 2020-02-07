package com.chibik.perf.java8.sequential;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@IncludedInReport
public class SortedStream {

    @Param({"5", "50", "500", "5000", "50000", "500000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Trial)
    public void setUpTrial() {
        System.gc();
        System.gc();
        System.gc();
    }

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i));
        }

        Collections.shuffle(entities, new Random(30));
    }

    @Benchmark
    @Comment("Create new ArrayList from entities, sort it and return")
    public Object manual() {
        ArrayList<StreamEntity> copy = new ArrayList<>(entities);
        copy.sort(comparing(StreamEntity::getKey));
        return copy;
    }

    @Benchmark
    @Comment("steam().sorted().collect(toList())")
    public Object stream() {
        return entities.stream()
                .sorted(comparing(StreamEntity::getKey))
                .collect(Collectors.toList());
    }

    @Benchmark
    @Comment("steam().parallel().sorted().collect(toList())")
    public Object streamParallel() {
        return entities.stream()
                .parallel()
                .sorted(comparing(StreamEntity::getKey))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SortedStream.class);
    }
}
