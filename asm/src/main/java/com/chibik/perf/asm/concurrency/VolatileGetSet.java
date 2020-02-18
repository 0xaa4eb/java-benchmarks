package com.chibik.perf.asm.concurrency;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly(complexity = 10)
public class VolatileGetSet {

    private volatile long volatileField;

    @Setup(Level.Iteration)
    public void setUp() {
        volatileField = 0;
    }

    @Benchmark
    public long get() {
        return volatileField;
    }

    @Benchmark
    public void set() {
        volatileField = 0xdeadbeef;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VolatileGetSet.class);
    }
}
