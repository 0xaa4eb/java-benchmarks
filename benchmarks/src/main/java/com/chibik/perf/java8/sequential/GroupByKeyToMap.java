package com.chibik.perf.java8.sequential;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10)
@Measurement(iterations = 20)
@IncludedInReport
@Comment("")
public class GroupByKeyToMap {

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
    @Comment("HashMap<>((int) (entities.size()/0.75) + 1) and ArrayList<>(1)(assuming we know " +
            "that in most cases only one element will be in the list)")
    public Object manualWithAdjustementToContainerSizes() {
        Map<String, List<StreamEntity>> map = new HashMap<>((int) (entities.size()/0.75) + 1);

        for(StreamEntity entity : entities) {
            List<StreamEntity> list = map.get(entity.getKey());

            if(list == null) {
                map.put(entity.getKey(), list = new ArrayList<>(1));
            }

            list.add(entity);
        }

        return map;
    }

    @Benchmark
    public Object manual() {
        Map<String, List<StreamEntity>> map = new HashMap<>();

        for(StreamEntity entity : entities) {
            List<StreamEntity> list = map.get(entity.getKey());

            if(list == null) {
                map.put(entity.getKey(), list = new ArrayList<>());
            }

            list.add(entity);
        }

        return map;
    }

    @Benchmark
    public Object stream() {
        return entities.stream().collect(Collectors.groupingBy(StreamEntity::getKey));
    }

    @Benchmark
    public Object parallelStream() {
        return entities.parallelStream().collect(Collectors.groupingBy(StreamEntity::getKey));
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(GroupByKeyToMap.class);
    }
}
