package com.chibik.perf.inline;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@OperationsPerInvocation(InlineFreqSizeBenchmark.OPERATIONS)
public class InlineFreqSizeBenchmark {

    /*
    If there would be any performance gain by removing temporary assignment to local variables, it will happily get rid of it.
    However, there is one caveat; the inline threshold for frequently executed methods depends on the bytecode size of the method;
    that is the original size of the bytecode. So if you make the bytecode of a method larger than needed, it can prevent inlining.
    And inlining is one of the most crucial optimizations because it provides the ability for further optimizations as we'll see.
    */
    public static final int OPERATIONS = 1000 * 1000;

    @State(Scope.Benchmark)
    static public class Triple {
        int a;
        int b;
        int c;

        int getA() {
            return a;
        }

        int getB() {
            return b;
        }

        int getC() {
            return c;
        }
    }

    static int large(Triple triple) {
        int a = triple.getA();
        int b = triple.getB();
        int c = triple.getC();
        return a + b + c;
    }

    static int small(Triple triple) {
        return triple.getA() + triple.getB() + triple.getC();
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    @Fork(jvmArgs = "-XX:FreqInlineSize=20")
    public int benchmarkSmall(Triple triple) {
        int v = 0;
        for (int k = 0; k < OPERATIONS; k++) {
            v = small(triple);
        }
        return v;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    @Fork(jvmArgs = "-XX:FreqInlineSize=20")
    public long benchmarkLargeWithLowInlineSize(Triple triple) {
        int v = 0;
        for (int k = 0; k < OPERATIONS; k++) {
            v = large(triple);
        }
        return v;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    @Fork(jvmArgs = "-XX:FreqInlineSize=21")
    public long benchmarkLargeWithHighInlineSize(Triple triple) {
        int v = 0;
        for (int k = 0; k < OPERATIONS; k++) {
            v = large(triple);
        }
        return v;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(InlineFreqSizeBenchmark.class);
    }
}
