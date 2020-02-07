package com.chibik.perf.java.exception;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*TryCatchFinally.thr -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@AvgTimeBenchmark
public class TryCatchFinally {

    private Lock lock;
    private int count;

    @Setup(Level.Iteration)
    public void setUp() {
        lock = new ReentrantLock(false);
    }

    @Benchmark
    public void test() {
        lock();

        try {

            count += 3;
            thr();

        } catch (Throwable e) {
            count += 5;
        } finally {
            unlock();
        }
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void lock() {
        lock.lock();
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void thr() throws Throwable {
        throw new Throwable("d");
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void unlock() {
        lock.unlock();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TryCatchFinally.class);
    }

}
