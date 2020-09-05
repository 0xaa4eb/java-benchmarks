package com.chibik.perf.concurrency.volatil;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("VarHandle has the same performance as volatile")
public class VolatileLoadVsVarHandle {

    private volatile long volatil;

    private static final VarHandle AA;

    static {
        try {
            AA = MethodHandles.lookup().findVarHandle(VolatileLoadVsVarHandle.class, "volatil", long.class);
        } catch (Exception e) {
            throw new RuntimeException("Static initializer err");
        }
    }

    @Setup(Level.Iteration)
    public void setUp() {
        volatil = ThreadLocalRandom.current().nextLong();
    }

    @Benchmark
    public long loadVolatile() {
        return volatil;
    }

    @Benchmark
    public long varHandleLoad() {
        return (long) AA.get(this);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VolatileLoadVsVarHandle.class);
    }
}
