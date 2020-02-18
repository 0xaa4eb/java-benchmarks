package com.chibik.perf.asm.intrinsics;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@PrintAssembly
public class CurrentThreadIsInterrupted {

    @Benchmark
    @Comment("Thread.currentThread().isInterrupted()")
    public boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CurrentThreadIsInterrupted.class);
    }
}
