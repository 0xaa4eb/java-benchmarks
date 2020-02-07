package com.chibik.perf.concurrency.other;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;
import util.Contended;

import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 20)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@IncludedInReport
@Comment("Strange test. Need to investigate. Concurrent update of volatile variable " +
        "inside object seems to be faster (somtimes) without @Contended annotation on the variable")
public class ObjectFalseSharingTest {

    private TestClass1 t11;
    private TestClass1 t12;

    private TestClass2 t21;
    private TestClass2 t22;

    @Setup(Level.Iteration)
    public void init() {
        System.gc();
        System.gc();
        System.gc();

        for(int i = 0; i < 50_000; i++) {
            t11 = new TestClass1();
            t12 = new TestClass1();

            t21 = new TestClass2();
            t22 = new TestClass2();
        }

        System.gc();
        System.gc();
        System.gc();

        for(int i = 0; i < 5_000; i++) {
            t11 = new TestClass1();
            t12 = new TestClass1();

            t21 = new TestClass2();
            t22 = new TestClass2();
        }

        System.gc();
        System.gc();
        System.gc();
    }

    @Benchmark
    @Group(value = "withoutContended")
    public int testWithoutContenedAnnotation1() {
        return t11.v++;
    }

    @Benchmark
    @Group(value = "withoutContended")
    public int testWithoutContenedAnnotation2() {
        return t12.v++;
    }

    @Benchmark
    @Group(value = "withContended")
    public int testWithContenedAnnotation1() {
        return t21.v++;
    }

    @Benchmark
    @Group(value = "withContended")
    public int testWithContenedAnnotation2() {
        return t22.v++;
    }

    private static class TestClass1 {
        public volatile int v;
    }

    private static class TestClass2 {
        @Contended
        public volatile int v;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(ObjectFalseSharingTest.class);
    }
}
