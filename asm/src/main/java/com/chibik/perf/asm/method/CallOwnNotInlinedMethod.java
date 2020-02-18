package com.chibik.perf.asm.method;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@PrintAssembly
public class CallOwnNotInlinedMethod {

    @Setup(Level.Iteration)
    public void setUp() {
    }

    @Benchmark
    public void callsMethod(Blackhole blackhole) {
        callableMethod(blackhole);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void callableMethod(Blackhole blackhole) {
        blackhole.consume(1);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallOwnNotInlinedMethod.class);
    }
}
