package com.chibik.perf.java.inlining;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*CallOverridenInsideBaseClass.overriddenMethod -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class CallOverridenInsideBaseClass {

    private ParentClass obj = new ChildClass();

    @Setup(Level.Iteration)
    public void setUp() {
        obj.setX(ThreadLocalRandom.current().nextInt(1024 * 1024));
    }

    @Benchmark
    public int overriddenMethod() {
        return obj.run();
    }

    public static class ParentClass {

        protected int x;

        public void setX(int x) {
            this.x = x;
        }

        public int overriddenMethod() {
            return x + 1;
        }

        public int run() {
            return overriddenMethod() + 1;
        }
    }

    public static class ChildClass extends ParentClass {

        @Override
        public int overriddenMethod() {
            return x - 1;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallOverridenInsideBaseClass.class);
    }

}
