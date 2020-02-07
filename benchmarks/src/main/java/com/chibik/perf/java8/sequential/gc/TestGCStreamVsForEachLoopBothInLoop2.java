package com.chibik.perf.java8.sequential.gc;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java8.sequential.StreamEntity;
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
public class TestGCStreamVsForEachLoopBothInLoop2 {

    @Param({"5"})
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
    public void streamSequential() {
        for(int i = 0; i < 50_000; i++) {
            entities.stream().forEach(this::consume);
        }
    }

    @Benchmark
    @Comment("")
    public void manual() {
        for(int i = 0; i < 50_000; i++) {
            for (StreamEntity entity : entities) {
                consume(entity);
            }
        }
    }

    private void consume(StreamEntity entity) {
        Blackhole.consumeCPU(entity.getIntValue());
    }

    /*
manual:·gc.alloc.rate.norm                   5  avgt    5       12.061 ± 3.358    B/op
streamSequential:·gc.alloc.rate.norm         5  avgt    5  4400013.231 ± 6.713    B/op
    * */
    public static void main(String[] args) {

        BenchmarkRunner.run(TestGCStreamVsForEachLoopBothInLoop2.class);
    }
}
