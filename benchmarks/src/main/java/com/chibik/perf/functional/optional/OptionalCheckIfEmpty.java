package com.chibik.perf.functional.optional;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark
@State(Scope.Thread)
public class OptionalCheckIfEmpty {

    private String value;

    @Setup(Level.Iteration)
    public void setUp() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            value = "";
        } else {
            if (ThreadLocalRandom.current().nextBoolean()) {
                value = "abc";
            } else {
                value = null;
            }
        }
    }

    @Benchmark
    public boolean optionalCheck() {
        return getOptional().orElse("").isEmpty();
    }

    @Benchmark
    public boolean oldStyleCheck() {
        var value = get();
        return value == null || value.isEmpty();
    }

    private Optional<String> getOptional() {
        return Optional.ofNullable(value);
    }

    private String get() {
        return value;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(OptionalCheckIfEmpty.class);
    }
}
