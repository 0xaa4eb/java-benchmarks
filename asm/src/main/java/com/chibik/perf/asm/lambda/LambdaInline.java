package com.chibik.perf.asm.lambda;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class LambdaInline {

    private volatile int counter = 0;

    @Benchmark
    public void callLambdaInPlace() {
        Runnable runnable = this::call;
        runnable.run();
    }

    private void call() {
        counter++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LambdaInline.class);
    }
}
