package com.chibik.perf.string;

import com.chibik.perf.util.SingleSnapshotBenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@SingleSnapshotBenchmark(batchSize = 500000, iterations = 200)
public class StringBufferVsStringBuilderSingleThreaded {

    private StringBuilder builder = new StringBuilder(5000000);

    private StringBuffer buffer = new StringBuffer(5000000);

    @Param({"a", "aaaaa", "aaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    private String appendable;

    @Setup(Level.Iteration)
    public void setUp() {
        builder.setLength(0);
        buffer.setLength(0);
    }

    @Benchmark
    public void appendToStringBuilder() {
        builder.append(appendable);
    }

    @Benchmark
    public void appendToStringBuffer() {
        buffer.append(appendable);
    }

    public static void main(String[] args) throws RunnerException {

    }
}