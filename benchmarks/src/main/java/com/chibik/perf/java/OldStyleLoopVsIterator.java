package com.chibik.perf.java;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
public class OldStyleLoopVsIterator {

    @Param({ "32", "1024", "32768" })
    int size;

    byte[] buff;

    @Setup
    public void setUp() {
        buff = new byte[size];
        for(int i = 0; i < buff.length; i++) {
            buff[i] = (byte) i;
        }
    }

    @Benchmark
    public int oldLoop() {
        int r = 0;
        for (int x = 0; x < buff.length; x++) {
            r ^= buff[x];
        }
        return r;
    }

    @Benchmark
    public int iteratedLoop() {
        int r = 0;
        for (byte x : buff) {
            r ^= x;
        }
        return r;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(OldStyleLoopVsIterator.class);
    }
}
