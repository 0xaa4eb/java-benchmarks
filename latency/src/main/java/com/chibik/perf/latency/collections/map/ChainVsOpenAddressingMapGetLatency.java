package com.chibik.perf.latency.collections.map;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.*;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@SingleShotBenchmark(batchSize = ChainVsOpenAddressingMapGetLatency.BATCH_SIZE)
public class ChainVsOpenAddressingMapGetLatency extends IndexedLatencyBenchmark {

    private static final int KEY = 46_387_223;
    private static final Integer KEY_BOXED = KEY;

    public static final int BATCH_SIZE = 100000;

    private interface StateHolder {

        int get();
    }

    private static class BoxedMapStateHolder extends Padder implements StateHolder {
        final Map<Integer, Integer> map;

        private BoxedMapStateHolder(Map<Integer, Integer> map) {
            this.map = map;
        }

        public int get() {
            return map.get(KEY_BOXED);
        }
    }

    private static class OpenAddressingStateHolder extends Padder implements StateHolder {
        final Int2IntMap map;

        private OpenAddressingStateHolder(Int2IntMap map) {
            this.map = map;
        }

        public int get() {
            return map.get(KEY);
        }
    }

    @Param({"HashMap", "Int2IntOpenHashMap"})
    private String mapImpl;

    private StateHolder[] stateHolders;

    private void initMap(Map<Integer, Integer> map) {
        Random r = new Random(120);
        for (int i = 0; i < 14; i++) {
            map.put(r.nextInt(), r.nextInt());
        }
        map.put(KEY_BOXED, 0xdead);
    }

    @Setup(Level.Trial)
    public void setUpTrial() {
        if (mapImpl.equals("HashMap")) {
            stateHolders = MemUtil.allocateArray(
                    BATCH_SIZE,
                    BoxedMapStateHolder.class,
                    () -> {
                        Map<Integer, Integer> map = new HashMap<>();
                        initMap(map);
                        return new BoxedMapStateHolder(map);
                    }
            );
        } else {
            stateHolders = MemUtil.allocateArray(
                    BATCH_SIZE,
                    OpenAddressingStateHolder.class,
                    () -> {
                        Int2IntMap map = new Int2IntOpenHashMap();
                        initMap(map);
                        return new OpenAddressingStateHolder(map);
                    });
        }
    }

    @Benchmark
    public void iterate(Blackhole blackhole) {
        blackhole.consume(stateHolders[getIndex()].get());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ChainVsOpenAddressingMapGetLatency.class);
    }
}
