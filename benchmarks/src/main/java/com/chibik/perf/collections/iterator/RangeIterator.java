package com.chibik.perf.collections.iterator;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark
@State(Scope.Thread)
public class RangeIterator {

    private int fromInclusive;
    private int toExclusive;

    @Setup(Level.Iteration)
    public void setUp() {
        fromInclusive = ThreadLocalRandom.current().nextInt(50);
        toExclusive = ThreadLocalRandom.current().nextInt(50) + 100;
    }

    public static Iterable<Integer> range(int fromInclusive, int toExclusive) {
        return () -> new Iterator<Integer>() {
            int cursor = fromInclusive;
            public boolean hasNext() { return cursor < toExclusive; }
            public Integer next() { return cursor++; }
        };
    }

    @Benchmark
    public int indexedLoop() {
        int res = 0;
        for (int i = fromInclusive; i < toExclusive; i++) {
            res = res + (res ^ i);
        }
        return res;
    }

    @Benchmark
    public int rangeLoop() {
        int res = 0;
        for (int v : range(fromInclusive, toExclusive)) {
            res = res + (res ^ v);
        }
        return res;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(RangeIterator.class);
    }
}
