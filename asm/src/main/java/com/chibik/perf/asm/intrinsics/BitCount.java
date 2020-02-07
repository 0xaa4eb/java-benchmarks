package com.chibik.perf.asm.intrinsics;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class BitCount {

    @Setup(Level.Iteration)
    public void setUp() {
    }

    private volatile int zzVolatileField = 0;

    @Benchmark
    @Comment("X64 popcnt instruction should be used")
    public int bitCount() {
        return Integer.bitCount(zzVolatileField);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(BitCount.class);
    }
}
