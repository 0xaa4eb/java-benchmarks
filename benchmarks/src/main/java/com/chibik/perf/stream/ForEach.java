package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

@AvgTimeBenchmark
@State(Scope.Thread)
public class ForEach {

    @Param({"50000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i, 10));
        }

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("")
    public void manual() {

        for (StreamEntity entity : entities) {
            Blackhole.consumeCPU(entity.getIntValue());
        }
    }

    @Benchmark
    @Comment("")
    public void streamParallelForEach() {

        entities.stream().parallel().forEach(x -> Blackhole.consumeCPU(x.getIntValue()));
    }

    @Benchmark
    @Comment("")
    public void streamParallelForEachOrdered() {

        entities.stream().parallel().forEachOrdered(x -> Blackhole.consumeCPU(x.getIntValue()));
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ForEach.class);
    }
}
