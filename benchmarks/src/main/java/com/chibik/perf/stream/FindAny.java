package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AvgTimeBenchmark
@State(Scope.Thread)
@Comment("Allocate array of entities and set entity to found at size/3 index")
public class FindAny {

    @Param({"500000"})
    private int size;

    private List<StreamEntity> entities;

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            entities.add(new StreamEntity("" + i, "" + i, 10));
        }

        int index = size / 3;
        entities.get(index).setIntValue(11);

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("")
    public StreamEntity manual() {

        for (StreamEntity entity : entities) {
            if (entity.getIntValue() > 10) {
                return entity;
            }
        }

        throw new RuntimeException("Not found entity with intVal > 10");
    }

    @Benchmark
    @Comment("entities.stream().parallel().filter(x -> x.getIntValue() > 10).findAny()")
    public StreamEntity streamParallel() {

        Optional<StreamEntity> any = entities.stream().parallel().filter(x -> x.getIntValue() > 10).findAny();

        if (any.isPresent()) {
            return any.get();
        } else {
            throw new RuntimeException("Not found entity with intVal > 10");
        }
    }

    @Benchmark
    @Comment("entities.stream().filter(x -> x.getIntValue() > 10).findAny()")
    public StreamEntity streamSequential() {

        Optional<StreamEntity> any = entities.stream().filter(x -> x.getIntValue() > 10).findAny();

        if (any.isPresent()) {
            return any.get();
        } else {
            throw new RuntimeException("Not found entity with intVal > 10");
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(FindAny.class);
    }
}
