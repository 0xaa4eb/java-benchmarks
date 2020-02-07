package com.chibik.perf.asm.method;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class EmptyMethod {

    @Setup(Level.Iteration)
    public void setUp() {
    }

    @Benchmark
    public void emptyMethod() {

    }

    @Benchmark
    public long returnDeadBeef() {
        return 0xdeadbeef;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(EmptyMethod.class);
    }
}
