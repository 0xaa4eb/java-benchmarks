package com.chibik.perf.java;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
public class Test1Test {

    private ThreadLocalRandom r;

    private TestInterface int1 = new Impl1();
    private TestInterface int2 = new Impl2();

    @Setup(Level.Iteration)
    public void setup() {
        r = ThreadLocalRandom.current();
    }

    @Benchmark
    public void test1(Blackhole bh) {
        int rand = r.nextInt(10);
        if(rand == 0) {
            int1.doSmth(bh);
        } else {
            int2.doSmth(bh);
        }
    }

    @Benchmark
    public void test2(Blackhole bh) {
        int rand = r.nextInt(10);
        if(rand != 0) {
            int1.doSmth(bh);
        } else {
            int2.doSmth(bh);
        }
    }

    @Benchmark
    public void test3(Blackhole bh) {
        int rand = r.nextInt(10);
        if(rand >= 5) {
            int1.doSmth(bh);
        } else {
            int2.doSmth(bh);
        }
    }

    public interface TestInterface {
        void doSmth(Blackhole bh);
    }

    public class Impl1 implements TestInterface {

        @Override
        public void doSmth(Blackhole bh) {
            bh.consumeCPU(1);
        }
    }

    public class Impl2 implements TestInterface {

        @Override
        public void doSmth(Blackhole bh) {
            bh.consumeCPU(1);
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(Test1Test.class);
    }

}
