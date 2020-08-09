package com.chibik.perf.asm.call;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@PrintAssembly
public class CallObjMethod {

    private static final Object obj = new Object();

    @Benchmark
    public void callObjectConsumer() {
        objectConsumer(obj); // put to rdx
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void objectConsumer(Object v) {

    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallObjMethod.class);
    }
}
