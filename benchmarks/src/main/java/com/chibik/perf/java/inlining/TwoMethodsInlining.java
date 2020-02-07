package com.chibik.perf.java.inlining;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java.inlining.storage.HashMapStorage;
import com.chibik.perf.java.inlining.storage.PojoStorage;
import com.chibik.perf.java.inlining.storage.Storage;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*TwoMethodsInlining.finalMethod -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class TwoMethodsInlining {

    private Storage storage1 = new HashMapStorage();
    private Storage storage2 = new PojoStorage();

    @Setup(Level.Iteration)
    public void setUp() {
        storage1.setX(String.valueOf(ThreadLocalRandom.current().nextInt(1024 * 1024)));
        storage2.setX(String.valueOf(ThreadLocalRandom.current().nextInt(1024 * 1024)));
    }

    @Benchmark
    public int finalMethod() {
        return doLogic0(storage1) + doLogic0(storage2);
    }

    @Benchmark
    public int finalMethodSameCore() {
        return doLogic1(storage1) + doLogic2(storage2);
    }

    @Benchmark
    public int finalMethodDup() {
        return doLogic1Dup(storage1) + doLogic2Dup(storage2);
    }

    public int doLogic0(Storage storage) {
        return logic(storage);
    }

    public int doLogic1(Storage storage) {
        return logic(storage);
    }

    public int doLogic2(Storage storage) {
        return logic(storage);
    }

    private int logic(Storage storage) {
        return storage.getX().length();
    }

    public int doLogic1Dup(Storage storage) {
        return storage.getX().length();
    }

    public int doLogic2Dup(Storage storage) {
        return storage.getX().length();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TwoMethodsInlining.class);
    }

}
