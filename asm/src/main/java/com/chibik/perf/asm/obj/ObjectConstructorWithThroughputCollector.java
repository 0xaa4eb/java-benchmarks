package com.chibik.perf.asm.obj;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AdditionalForkJvmKeys;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly(complexity = 30)
@AdditionalForkJvmKeys("-XX:+UseParallelOldGC")
public class ObjectConstructorWithThroughputCollector {

    private Class1 t;

    private static class Class1 {
        private long l1 = 0xdeadbeef;
        private long l2 = 0xdeaddead;
        private long l3 = 0xcafebabe;

        public Class1() {
        }

        public long getL1() {
            return l1 + l2 + l3;
        }
    }

    @Benchmark
    public void createAnInstance() {
        t = new Class1();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ObjectConstructorWithThroughputCollector.class);
    }
}
