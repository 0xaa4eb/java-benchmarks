package com.chibik.perf.concurrency.volatil;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@IncludedInReport
@Comment("There is no weak CAS on x64")
public class WeakCasVsStrongCas {

    private final AtomicLong atomicLong = new AtomicLong();

    @Setup(Level.Iteration)
    public void setUp() {
        atomicLong.set(0);

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    public boolean strongCAS() {
        long prev = atomicLong.get();
        return atomicLong.compareAndSet(prev, 1 - prev);
    }

    @Benchmark
    public boolean weakCAS() {
        long prev = atomicLong.get();
        return atomicLong.weakCompareAndSetVolatile(prev, 1 - prev);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(WeakCasVsStrongCas.class);
    }
}
