package com.chibik.perf.java.inlining.storage;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*TestStorageImpl.getX -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class TestStorageImpl {

    private Storage storage = new HashMapStorage();
    private Storage storage2 = new PojoStorage2();
    private Storage mapStorage = new HashMapStorage();
    private Storage pojo2 = new PojoStorage2();

    @Setup(Level.Invocation)
    public void setUp() {
        if(ThreadLocalRandom.current().nextBoolean()) {
            storage = mapStorage;
        } else {
            storage = pojo2;
        }
        storage.setX("X123" + (ThreadLocalRandom.current().nextInt(1024)));
//        storage2.setX("X123" + (ThreadLocalRandom.current().nextInt(1024)));
    }

    @Benchmark
    public boolean getX() {
        return storage.getClass() == PojoStorage2.class;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TestStorageImpl.class);
    }

}
