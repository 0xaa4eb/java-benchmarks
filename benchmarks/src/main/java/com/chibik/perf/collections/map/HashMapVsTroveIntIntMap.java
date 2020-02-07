package com.chibik.perf.collections.map;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import com.chibik.perf.util.SingleSnapshotBenchmark;
import gnu.trove.map.hash.TIntIntHashMap;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleSnapshotBenchmark(batchSize = HashMapVsTroveIntIntMap.BATCH_SIZE)
@Comment("Initialize array of a random numbers and put all them to the map. 1 is used as a value")
public class HashMapVsTroveIntIntMap {

    public static final int BATCH_SIZE = 1000000;

    private Map<Integer, Integer> hashMap;

    private Map<Integer, Integer> linkedHashMap;

    private TIntIntHashMap tIntIntHashMap;

    private int[] data = new int[BATCH_SIZE];
    private int index;

    @Setup(Level.Iteration)
    public void setUp() {
        Random r = new Random(30L);
        for(int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(50000000);
        }

        index = 0;

        hashMap = new HashMap<>();
        tIntIntHashMap = new TIntIntHashMap();
        linkedHashMap = new LinkedHashMap<>();
    }

    @Benchmark
    public void hashMap() {
        hashMap.put(data[index], 1);
    }

    @Benchmark
    public void linkedHashMap() {
        linkedHashMap.put(data[index], 1);
    }

    @Benchmark
    public void tIntIntHashMap() {
        tIntIntHashMap.put(data[index], 1);
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    @TearDown(Level.Iteration)
    public void validate() {
        int sumSize = tIntIntHashMap.size() + hashMap.size() + linkedHashMap.size();
        if(sumSize != 990161) {
            throw new RuntimeException("Expected at least one element but was " + sumSize);
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(HashMapVsTroveIntIntMap.class);
    }
}
