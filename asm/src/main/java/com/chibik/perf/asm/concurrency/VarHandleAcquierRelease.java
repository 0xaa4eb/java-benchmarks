package com.chibik.perf.asm.concurrency;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@State(Scope.Benchmark)
@PrintAssembly
public class VarHandleAcquierRelease {

    private static final VarHandle AA;

    static {
        try {
            AA = MethodHandles.lookup().findVarHandle(VarHandleAcquierRelease.class, "field", long.class);
        } catch (Exception e) {
            throw new RuntimeException("Static initializer err");
        }
    }

    private long field;

    @Setup(Level.Iteration)
    public void setUp() {
        field = 0;
    }

    @Benchmark
    public void acquire(Blackhole bh) {
        bh.consume((long) AA.getAcquire(this));
    }

    @Benchmark
    public void release(Blackhole bh) {
        AA.setRelease(this, 0xdeadbeef);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VarHandleAcquierRelease.class);
    }
}
