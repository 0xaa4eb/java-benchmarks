package com.chibik.perf.functional;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark
@State(Scope.Thread)
public class RangeCheck {

    private static class Range {
        private final int from;
        private final int to;

        private Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean in(int val) {
            return val >= from && val < to;
        }
    }

    private int value;
    private int fromInclusive;
    private int toExclusive;

    @Setup(Level.Iteration)
    public void setUp() {
        fromInclusive = ThreadLocalRandom.current().nextInt(50);
        toExclusive = ThreadLocalRandom.current().nextInt(50) + 100;
        if (ThreadLocalRandom.current().nextBoolean()) {
            value = (toExclusive + fromInclusive) / 2;
        } else {
            if (ThreadLocalRandom.current().nextBoolean()) {
                value = 0;
            } else {
                value = toExclusive + 1;
            }
        }
    }

    @Benchmark
    public boolean oldStyleCheck() {
        return value >= fromInclusive && value < toExclusive;
    }

    @Benchmark
    public boolean functionalRangeCheck() {
        return new Range(fromInclusive, toExclusive).in(value);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(RangeCheck.class);
    }
}
