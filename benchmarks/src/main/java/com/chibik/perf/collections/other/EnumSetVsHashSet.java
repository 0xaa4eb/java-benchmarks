package com.chibik.perf.collections.other;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import com.chibik.perf.util.SingleSnapshotBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.EnumSet;
import java.util.HashSet;

@State(Scope.Benchmark)
@SingleSnapshotBenchmark(batchSize = 100000)
@Comment("Enum with 20 values is used. Put 6 of them to hash set and enum set")
public class EnumSetVsHashSet {

    private EnumSet<TestEnum> enumSet;

    private HashSet<TestEnum> hashSet;

    @Setup(Level.Invocation)
    public void setUp() {
        enumSet = EnumSet.noneOf(TestEnum.class);
        hashSet = new HashSet<>();
    }

    @Benchmark
    public void enumSet() {
        enumSet.add(TestEnum.A20);
        enumSet.add(TestEnum.A4);
        enumSet.add(TestEnum.A12);
        enumSet.add(TestEnum.A18);
        enumSet.add(TestEnum.A1);
        enumSet.add(TestEnum.A4);
    }

    @Benchmark
    public void hashSet() {
        hashSet.add(TestEnum.A20);
        hashSet.add(TestEnum.A4);
        hashSet.add(TestEnum.A12);
        hashSet.add(TestEnum.A18);
        hashSet.add(TestEnum.A1);
        hashSet.add(TestEnum.A4);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(EnumSetVsHashSet.class);
    }

    public enum TestEnum {
        A1,
        A2,
        A3,
        A4,
        A5,
        A6,
        A7,
        A8,
        A9,
        A10,
        A11,
        A12,
        A13,
        A14,
        A15,
        A16,
        A17,
        A18,
        A19,
        A20
    }
}
