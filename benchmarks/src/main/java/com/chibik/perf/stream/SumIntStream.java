package com.chibik.perf.stream;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.*;

@AvgTimeBenchmark
@State(Scope.Thread)
public class SumIntStream {

    @Param({"5", "50", "500", "5000", "50000", "1000000"})
    private int size;

    private int[] entities;

    @Setup(Level.Trial)
    public void setUpTrial() {
        System.gc();
        System.gc();
        System.gc();
    }

    @Setup(Level.Iteration)
    public void setUp() {
        entities = new int[size];

        Random r = new Random(30);

        for (int i = 0; i < size; i++) {
            entities[i] = r.nextInt(1000);
        }
    }

    @Benchmark
    @Comment("For each loop")
    public Object manual() {
        int sum = 0;
        for (int v : entities) {
            sum += v;
        }
        return sum;
    }

    @Benchmark
    @Comment("")
    public Object stream() {
        return Arrays.stream(entities).sum();
    }

    @Benchmark
    @Comment("")
    public Object streamParallel() {
        return Arrays.stream(entities).parallel().sum();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SumIntStream.class);
    }
}
