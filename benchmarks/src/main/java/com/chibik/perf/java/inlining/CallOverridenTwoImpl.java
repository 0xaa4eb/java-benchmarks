package com.chibik.perf.java.inlining;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*CallOverridenTwoImpl.overriddenMethod -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class CallOverridenTwoImpl {

    private ParentClass obj = new ChildClass();
    private ParentClass obj2 = new ChildClass2();
    private ParentClass obj3 = new ChildClass3();

    @Setup(Level.Iteration)
    public void setUp() {
        obj.setX(ThreadLocalRandom.current().nextInt(1024 * 1024));
        obj2.setX(ThreadLocalRandom.current().nextInt(1024 * 1024));
        obj3.setX(ThreadLocalRandom.current().nextInt(1024 * 1024));
    }

    @Benchmark
    public int finalMethod() {
        return obj.finalMethod();
    }

    @Benchmark
    public int overriddenMethod() {
        return obj3.overriddenMethod();
    }

    public static class ParentClass {

        protected int x;

        public void setX(int x) {
            this.x = x;
        }

        public int overriddenMethod() {
            return x + 1;
        }

        public int finalMethod() {
            return x + 1;
        }
    }

    public static class ChildClass extends ParentClass {

        @Override
        public int overriddenMethod() {
            return x - 1;
        }
    }

    public static class ChildClass2 extends ParentClass {

        @Override
        public int overriddenMethod() {
            return x - 1;
        }
    }

    public static class ChildClass3 extends ParentClass {

        @Override
        public int overriddenMethod() {
            return x - 1;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallOverridenTwoImpl.class);
    }

}
