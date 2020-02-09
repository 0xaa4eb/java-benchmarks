package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@AvgTimeBenchmark
public class MeasureAllocator {

    @Setup(Level.Iteration)
    public void gc() {
        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Comment("new Object();")
    public Object object() {
        return new Object();
    }

    @Benchmark
    @Comment("allocate object with 3 strings inside all set to NULL")
    public Object createAlloca1() {
        return new AllocaClass1();
    }

    @Benchmark
    @Comment("allocate object wuth 6 strings inside all set to NULL")
    public Object createAlloca2() {
        return new AllocaClass2();
    }

    @Benchmark
    @Comment("allocate object wuth 9 strings inside all set to NULL")
    public Object createAlloca3() {
        return new AllocaClass3();
    }

    @Benchmark
    @Comment("allocate new int[10]")
    public Object newInt10() {
        return new int[10];
    }

    @Benchmark
    @Comment("allocate new int[100]")
    public Object newInt100() {
        return new int[100];
    }

    @Benchmark
    @Comment("allocate new int[500]")
    public Object newInt500() {
        return new int[500];
    }

    @Benchmark
    @Comment("allocate new int[1000]")
    public Object newInt1000() {
        return new int[1000];
    }

    public static class AllocaClass1 {
        private String s1;
        private String s2;
        private String s3;
    }

    public static class AllocaClass2 extends AllocaClass1 {
        private String s4;
        private String s5;
        private String s6;
    }

    public static class AllocaClass3 extends AllocaClass2 {
        private String s7;
        private String s8;
        private String s9;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(MeasureAllocator.class);
    }
}
