package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@AvgTimeBenchmark
@State(Scope.Thread)
@Comment("Allocate array of entities and set entity to found at size/3 index")
public class FindMinimum {

    @Param({"500000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        Random random = new Random(30);
        for (int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i, 1 + random.nextInt(10000)));
        }

        int index = size / 3;
        entities.get(index).setIntValue(0);

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("")
    public StreamEntity manual() {

        int currentMin = Integer.MAX_VALUE;
        StreamEntity currentEntity = null;

        for (StreamEntity entity : entities) {
            if (entity.getIntValue() < currentMin) {
                currentMin = entity.getIntValue();
                currentEntity = entity;
            }
        }

        return currentEntity;
    }

    @Benchmark
    @Comment("entities.parallelStream().filter(x -> x.getIntValue() > 10).min(..)")
    public StreamEntity streamParallel() {

        Optional<StreamEntity> any = entities.parallelStream().min((o1, o2) -> o1.getIntValue() - o2.getIntValue());

        if (any.isPresent()) {
            return any.get();
        } else {
            throw new RuntimeException("Not found entity with intVal > 10");
        }
    }

    @Benchmark
    @Comment("entities.stream().filter(x -> x.getIntValue() > 10).findAny()")
    public StreamEntity streamSequential() {

        Optional<StreamEntity> any = entities.stream().min((o1, o2) -> o1.getIntValue() - o2.getIntValue());

        if (any.isPresent()) {
            return any.get();
        } else {
            throw new RuntimeException("Not found entity with intVal > 10");
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(FindMinimum.class);
    }
}
