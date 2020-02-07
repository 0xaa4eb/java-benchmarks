package com.chibik.perf.java8.sequential;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@IncludedInReport
public class ForEach {

    @Param({"50000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i, 10));
        }

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("")
    public void manual() {

        for(StreamEntity entity : entities) {
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
