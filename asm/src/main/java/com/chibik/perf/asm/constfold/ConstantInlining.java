package com.chibik.perf.asm.constfold;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class ConstantInlining {

    private static final long field = 0xdeadbeef;

    @Setup(Level.Iteration)
    public void setUp() {
    }

    @Benchmark
    public long returnStringLen() {
        return /* field = 0xdeadbeef */ field;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ConstantInlining.class);
    }
}
