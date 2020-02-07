package com.chibik.perf.asm.intrinsics;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@PrintAssembly
public class CurrentThread {

    @Benchmark
    public Thread currentThread() {
        return Thread.currentThread();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CurrentThread.class);
    }
}
