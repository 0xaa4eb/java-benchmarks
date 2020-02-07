package com.chibik.perf.java;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class TypeConversionIntToOthers {

    private int aInt = 2;

    private int bInt = 3;

    @Benchmark
    public int intToInt() {
        return (int) aInt;
    }

    @Benchmark
    public char intToChar() {
        return (char) aInt;
    }

    @Benchmark
    public short intToShort() {
        return (short) bInt;
    }

    @Benchmark
    public float intToFloat() {
        return (float) aInt;
    }

    @Benchmark
    public long intToLong() {
        return (long) aInt;
    }

    @Benchmark
    public byte intToByte() {
        return (byte) aInt;
    }

    @Benchmark
    public double intToDouble() {
        return (double) aInt;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(TypeConversionIntToOthers.class);
    }
}
