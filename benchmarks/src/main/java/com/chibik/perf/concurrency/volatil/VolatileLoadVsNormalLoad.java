package com.chibik.perf.concurrency.volatil;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@IncludedInReport
@Comment("Same MOV instruction for x64")
public class VolatileLoadVsNormalLoad {

    @Contended
    private volatile long volatil;

    @Contended
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
