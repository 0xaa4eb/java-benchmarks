package com.chibik.perf.concurrency.volatil;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("Same MOV instruction for x64")
public class VolatileLoadVsNormalLoad {

    private volatile long volatil;

    private long normal;

    @Setup(Level.Iteration)
    public void setUp() {
        volatil = 0xdeadbeef;
        normal = 0xcafebabe;
    }

    @Benchmark
    public long loadVolatile() {
        return volatil;
    }

    @Benchmark
    public long loadNormal() {
        return normal;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VolatileLoadVsNormalLoad.class);
    }

}
