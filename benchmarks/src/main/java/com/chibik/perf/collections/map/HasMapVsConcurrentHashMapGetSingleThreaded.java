package com.chibik.perf.collections.map;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.SingleShotBenchmark;
import gnu.trove.map.hash.TLongLongHashMap;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = HasMapVsConcurrentHashMapGetSingleThreaded.BATCH_SIZE, timeUnit = TimeUnit.MICROSECONDS, iterations = 5)
@Comment("Initialize array of a random numbers and put all them to the map. " +
        "Test measures get() method time. 1 is used as a value")
public class HasMapVsConcurrentHashMapGetSingleThreaded {

    public static final int BATCH_SIZE = 1000000;

    private ConcurrentMap<Long, Long> concurrentMap;

    private Map<Long, Long> hashMap;

    private TLongLongHashMap tLongLongHashMap;

    private long[] data = new long[BATCH_SIZE];
    private int index;

    {
        Random r = new Random(30L);
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(50000000);
        }
    }

    @Setup(Level.Iteration)
    public void setUp() {
        concurrentMap = new ConcurrentHashMap<>();
        hashMap = new HashMap<>();
        tLongLongHashMap = new TLongLongHashMap();

        index = 0;
        for (int i = 0; i < data.length; i++) {
            concurrentMap.put(data[i], 1L);
            hashMap.put(data[i], 1L);
            tLongLongHashMap.put(data[i], 1L);
        }
    }

    @Benchmark
    public long concurrentHashMap() {
        return concurrentMap.get(data[index]);
    }

    @Benchmark
    public long hashMap() {
        return hashMap.get(data[index]);
    }

    @Benchmark
    public long tLongLongHashMap() {
        return tLongLongHashMap.get(data[index]);
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    @TearDown(Level.Iteration)
    public void end() {
        int finalSize = concurrentMap.size() + hashMap.size() + tLongLongHashMap.size();
        if (finalSize != 2970483) {
            throw new RuntimeException("should be 990161 but was " + finalSize);
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.runWithoutFork(
                HasMapVsConcurrentHashMapGetSingleThreaded.class
        );
    }
}
