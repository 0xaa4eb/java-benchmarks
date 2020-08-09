package com.chibik.perf.asm.call;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class CallIntMethod {

    @Benchmark
    public void callIntIntIntConsumer() {
        intIntConsumer(0xdead, 0xbeef, 0xf00d);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void intIntConsumer(int v1, int v2, int v3) {

    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallIntMethod.class);
    }
}
