package com.chibik.perf.collections.map;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("Initialize array of a random numbers and put all them to the map. 1 is used as a value")
public class MapOfMapVsSingleMap {

    private Map<String, Map<String, Map<String, MapEntity>>> map;

    private Map<MapKey, MapEntity> map2;

    @Setup(Level.Iteration)
    public void setUp() {
        map = new HashMap<>();
        map2 = new HashMap<>();

        for (int i = 0; i < 5000; i++) {
            String k1 = "" + (i % 1000);
            String k2 = "" + (i % 3);
            String k3 = "" + (i % 7);
            MapEntity entity = new MapEntity(k1, k2, k3);
            Map<String, MapEntity> resolvedMap = resolveToLastMap(k1, k2);
            resolvedMap.put(entity.getKey3(), entity);

            MapEntity entity2 = new MapEntity(k1, k2, k3);
            MapKey key = new MapKey(k1, k2, k3);
            map2.put(key, entity2);
        }

        System.gc();
        System.gc();
        System.gc();
    }

    private Map<String, MapEntity> resolveToLastMap(String key1, String key2) {
        Map<String, Map<String, MapEntity>> level1Map = map.get(key1);

        if (level1Map == null) {
            map.put(key1, level1Map = new HashMap<>());
        }

        Map<String, MapEntity> level2Map = level1Map.get(key2);

        if (level2Map == null) {
            level1Map.put(key2, level2Map = new HashMap<>());
        }

        return level2Map;
    }

    @Benchmark
    public void testMapOfMapOfMap() {
        for (int i = 0; i < 5000; i++) {
            String k1 = "" + (i % 1000);
            String k2 = "" + (i % 3);
            String k3 = "" + (i % 7);

            MapEntity entity = resolveToLastMap(k1, k2).get(k3);
            entity.setValue(entity.getValue() + 1);
        }
    }

    @Benchmark
    public void testSingleMap() {
        for (int i = 0; i < 5000; i++) {
            String k1 = "" + (i % 1000);
            String k2 = "" + (i % 3);
            String k3 = "" + (i % 7);

            MapEntity entity = map2.get(new MapKey(k1, k2, k3));
            entity.setValue(entity.getValue() + 1);
        }
    }

    private static class MapEntity {

        private String key1;
        private String key2;
        private String key3;
        private long value;

        public MapEntity(String key1, String key2, String key3) {
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
        }

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getKey2() {
            return key2;
        }

        public void setKey2(String key2) {
            this.key2 = key2;
        }

        public String getKey3() {
            return key3;
        }

        public void setKey3(String key3) {
            this.key3 = key3;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private static class MapKey {
        private final String key1;
        private final String key2;
        private final String key3;

        private MapKey(String key1, String key2, String key3) {
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
        }

        public String getKey1() {
            return key1;
        }

        public String getKey2() {
            return key2;
        }

        public String getKey3() {
            return key3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MapKey mapKey = (MapKey) o;

            if (key1 != null ? !key1.equals(mapKey.key1) : mapKey.key1 != null) return false;
            if (key2 != null ? !key2.equals(mapKey.key2) : mapKey.key2 != null) return false;
            return key3 != null ? key3.equals(mapKey.key3) : mapKey.key3 == null;
        }

        @Override
        public int hashCode() {
            int result = key1 != null ? key1.hashCode() : 0;
            result = 31 * result + (key2 != null ? key2.hashCode() : 0);
            result = 31 * result + (key3 != null ? key3.hashCode() : 0);
            return result;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(MapOfMapVsSingleMap.class);
    }
}
