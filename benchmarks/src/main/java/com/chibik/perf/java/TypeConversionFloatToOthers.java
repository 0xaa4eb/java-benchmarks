package com.chibik.perf.java;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class TypeConversionFloatToOthers {

    private float a = 2f;

    @Benchmark
    public double floatToDouble() {
        return (double) a;
    }

    @Benchmark
    public int floatToInt() {
        return (int) a;
    }

    @Benchmark
    public float floatToFloat() {
        return (float) a;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(TypeConversionFloatToOthers.class);
    }
}