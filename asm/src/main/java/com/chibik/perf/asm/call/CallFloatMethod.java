package com.chibik.perf.asm.call;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@PrintAssembly
public class CallFloatMethod {

    @Benchmark
    public void callDoubleDoubleDoubleConsumer() {
        doubleDoubleDoubleConsumer(5234.0, 1231353.0, 1231345.0);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void doubleDoubleDoubleConsumer(double v1, double v2, double v3) {

    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallFloatMethod.class);
    }
}
