package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AvgTimeBenchmark
@State(Scope.Thread)
public class MapListToList {

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
    @Comment("Allocate list and do for each loop")
    public Object manual() {
        List<String> list = new ArrayList<>(entities.size());

        for (StreamEntity entity : entities) {
            list.add(entity.getKey());
        }

        return list;
    }

    @Benchmark
    @Comment("entities.stream().map(StreamEntity::getKey).collect(toList())")
    public Object stream() {
        return entities.stream().map(StreamEntity::getKey).collect(toList());
    }

/*
    @Benchmark
    @Comment("entities.stream().parallel().map(StreamEntity::getKey).collect(toList())")
    public Object streamParallel() {
        return entities.stream().parallel().map(StreamEntity::getKey).collect(toList());
    }
*/

    public static void main(String[] args) {
        BenchmarkRunner.run(MapListToList.class);
    }
}
