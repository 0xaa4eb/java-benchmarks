package com.chibik.perf.concurrency.volatil;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@AvgTimeBenchmark(warmupIterations = 5, iterations = 5)
public class VolatileStoreVsVarHandle {

    private volatile long volatil;

    private static final VarHandle AA;

    static {
        try {
            AA = MethodHandles.lookup().findVarHandle(VolatileStoreVsVarHandle.class, "volatil", long.class);
        } catch (Exception e) {
            throw new RuntimeException("Static initializer err");
        }
    }

    @Setup(Level.Iteration)
    public void setUp() {
        volatil = ThreadLocalRandom.current().nextLong();
    }

    @Benchmark
    public void volatileStore() {
        volatil = 0xf00d;
    }

    @Benchmark
    public void varHandleStore() {
        AA.setVolatile(this, 0xf00d);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VolatileStoreVsVarHandle.class);
    }
}
