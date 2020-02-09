package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@AvgTimeBenchmark
@State(Scope.Thread)
public class SumStream {

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

        for (int i = 0; i < size; i++) {
            StreamEntity entity = new StreamEntity("" + i, "" + i);
            entity.setIntValue(i);
            entities.add(entity);
        }

        Collections.shuffle(entities, new Random(30));
    }

    @Benchmark
    @Comment("")
    public Object manual() {
        int sum = 0;
        for (StreamEntity entity : entities) {
            sum += entity.getIntValue();
        }
        return sum;
    }

    @Benchmark
    @Comment("")
    public Object stream() {
        return entities.stream()
                .map(StreamEntity::getIntValue)
                .reduce(0, (integer, integer2) -> integer + integer2);
    }

    @Benchmark
    @Comment("")
    public Object streamParallel() {
        return entities
                .parallelStream()
                .map(StreamEntity::getIntValue)
                .reduce(0, (integer, integer2) -> integer + integer2);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SumStream.class);
    }
}
