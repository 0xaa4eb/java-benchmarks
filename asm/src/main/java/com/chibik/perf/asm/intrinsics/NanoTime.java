package com.chibik.perf.asm.intrinsics;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@PrintAssembly
public class NanoTime {

    @Benchmark
    @Comment("System.nanoTime()")
    public long getNanoTime() {
        return System.nanoTime();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(NanoTime.class);
    }
}
