package com.chibik.perf.collections.map;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.SingleShotBenchmark;
import com.chibik.perf.util.SingleSnapshotIndexedBenchmark;
import gnu.trove.map.hash.TLongLongHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = LongLongHashMapsReadOnly.BATCH_SIZE, timeUnit = TimeUnit.MICROSECONDS, iterations = 5)
@Comment("Initialize array of a random numbers and put all them to the map. " +
        "Test measures get() method time. 1 is used as a value")
public class LongLongHashMapsReadOnly extends SingleSnapshotIndexedBenchmark {

    public static final int BATCH_SIZE = 1000000;

    private ConcurrentMap<Long, Long> concurrentMap;

    private Map<Long, Long> hashMap;

    private Long2LongMap fastutilHashMap;

    private Long2LongMap fastutilLinkedHashMap;

    private long[] data = new long[BATCH_SIZE];

    {
        Random r = new Random(randomSeed);
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(50000000);
        }
    }

    @Setup(Level.Trial)
    public void setUp() {
        concurrentMap = new ConcurrentHashMap<>();
        hashMap = new HashMap<>();
        fastutilHashMap = new Long2LongOpenHashMap();
        fastutilLinkedHashMap = new Long2LongLinkedOpenHashMap();
        for (long key : data) {
            long value = ThreadLocalRandom.current().nextLong();
            concurrentMap.put(key, value);
            hashMap.put(key, value);
            fastutilHashMap.put(key, value);
            fastutilLinkedHashMap.put(key, value);
        }
    }

    @Benchmark
    public long concurrentHashMap() {
        return concurrentMap.get(data[getIndex()]);
    }

    @Benchmark
    public long hashMap() {
        return hashMap.get(data[getIndex()]);
    }

    @Benchmark
    public long fastutilOpenHashMap() {
        return fastutilHashMap.get(data[getIndex()]);
    }

    @Benchmark
    public long fastutilOpenLinkedHashMap() {
        return fastutilLinkedHashMap.get(data[getIndex()]);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LongLongHashMapsReadOnly.class);
    }
}
