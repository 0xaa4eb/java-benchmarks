package com.chibik.perf.asm.concurrency;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.atomic.AtomicLong;

@State(Scope.Benchmark)
@PrintAssembly(complexity = 10)
public class AtomicLongMethods {

    private final AtomicLong atomicLong = new AtomicLong(0);

    @Benchmark
    public long get() {
        return atomicLong.get();
    }

    @Benchmark
    public long incrementAndGet() {
        return atomicLong.incrementAndGet();
    }

    @Benchmark
    public void setOrdered() {
        atomicLong.lazySet(0xdeadbeef);
    }

    @Benchmark
    public void set() {
        atomicLong.set(0xdeadbeef);
    }

    @Benchmark
    public boolean strongCas() {
        return atomicLong.compareAndSet(0xdeadbeef, 0xcafebabe);
    }

    @Benchmark
    public boolean weakCasPlain() {
        return atomicLong.weakCompareAndSetPlain(0xdeadbeef, 0xcafebabe);
    }

    @Benchmark
    public boolean weakCas() {
        return atomicLong.weakCompareAndSetVolatile(0xdeadbeef, 0xcafebabe);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(AtomicLongMethods.class);
    }
}
