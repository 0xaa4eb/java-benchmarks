package com.chibik.perf.concurrency.volatil;

import java.util.concurrent.atomic.AtomicLong;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("There is no weak CAS on x64")
public class XaddVsLazySetIncrement {

    private final AtomicLong variable = new AtomicLong();

    @Setup(Level.Iteration)
    public void setUp() {
        variable.set(0);

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    public void cmpXchg() {
        long val = variable.get();
        variable.compareAndSet(val, val + 1);
    }

    @Benchmark
    public void incXadd() {
        variable.incrementAndGet();
    }

    @Benchmark
    public void lazySetInc() {
        variable.lazySet(variable.get() + 1);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(XaddVsLazySetIncrement.class);
    }
}
