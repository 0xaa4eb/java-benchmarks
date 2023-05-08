package com.chibik.perf.ea;

import java.util.Optional;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import com.chibik.perf.BenchmarkRunner;

public class OptionalBenchmark {
    private record DataObject(String someField) {
    }

    private static final char STATUS_IF_SOME_FIELD_EXISTS = 'A';
    private static final char STATUS_IF_SOME_FIELD_IS_MISSING = 'B';

    @State(Scope.Thread)
    public static class DataObjectState {
        final DataObject dataObjectWithNullField = new DataObject(null);
        final DataObject dataObjectWithNonNullField = new DataObject("abc");
    }

    @Benchmark
    public void withOptionalNull(final Blackhole blackhole, final DataObjectState state) {
        final Character result = Optional.ofNullable(state.dataObjectWithNullField.someField)
            .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
            .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
        blackhole.consume(result);
    }

    @Benchmark
    public void withOptionalNonNull(final Blackhole blackhole, final DataObjectState state) {
        final Character result = Optional.ofNullable(state.dataObjectWithNonNullField.someField)
            .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
            .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
        blackhole.consume(result);
    }

    @Benchmark
    public void withTernaryNull(final Blackhole blackhole, final DataObjectState state) {
        final Character result = state.dataObjectWithNullField.someField == null
            ? STATUS_IF_SOME_FIELD_IS_MISSING
            : STATUS_IF_SOME_FIELD_EXISTS;
        blackhole.consume(result);
    }

    @Benchmark
    public void withTernaryNonNull(final Blackhole blackhole, final DataObjectState state) {
        final Character result = state.dataObjectWithNonNullField.someField == null
            ? STATUS_IF_SOME_FIELD_IS_MISSING
            : STATUS_IF_SOME_FIELD_EXISTS;
        blackhole.consume(result);
    }

    public static void main(String[] args) throws RunnerException {
        BenchmarkRunner.run(OptionalBenchmark.class);
    }
}
