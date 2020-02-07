package com.chibik.perf.concurrency.other;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;
import util.Contended;

/*
Benchmark                                                    Mode  Cnt   Score    Error  Units
RestrictContendedTest.addRestrict                            avgt    5  45.894 ± 23.033  ns/op
RestrictContendedTest.addRestrict:testAddRestrictContended1  avgt    5  42.551 ±  7.738  ns/op
RestrictContendedTest.addRestrict:testAddRestrictContended2  avgt    5  49.236 ± 49.485  ns/op
RestrictContendedTest.addRestrict:·asm                       avgt          NaN             ---
RestrictContendedTest.noFlag                                 avgt    5   5.710 ±  0.743  ns/op
RestrictContendedTest.noFlag:testNoFlag1                     avgt    5   5.693 ±  0.694  ns/op
RestrictContendedTest.noFlag:testNoFlag2                     avgt    5   5.726 ±  0.796  ns/op
RestrictContendedTest.noFlag:·asm                            avgt          NaN             ---
RestrictContendedTest.noRestrict                             avgt    5   5.533 ±  0.118  ns/op
RestrictContendedTest.noRestrict:testNoRestrictContended1    avgt    5   5.534 ±  0.132  ns/op
RestrictContendedTest.noRestrict:testNodRestrictContended2   avgt    5   5.532 ±  0.108  ns/op
RestrictContendedTest.noRestrict:·asm                        avgt          NaN             ---
*/

@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
public class RestrictContendedTest {

    @Contended
    private volatile long v1;
    @Contended
    private volatile long v2;

    @Benchmark
    @Group(value = "addRestrict")
    @Fork(value = 1, jvmArgs = "-XX:+RestrictContended")
    public void testAddRestrictContended1() {
        v1++;
    }

    @Benchmark
    @Group(value = "addRestrict")
    @Fork(value = 1, jvmArgs = "-XX:+RestrictContended")
    public void testAddRestrictContended2() {
        v2++;
    }

    @Benchmark
    @Group(value = "noRestrict")
    @Fork(value = 1, jvmArgs = "-XX:-RestrictContended")
    public void testNoRestrictContended1() {
        v1++;
    }

    @Benchmark
    @Group(value = "noRestrict")
    @Fork(value = 1, jvmArgs = "-XX:-RestrictContended")
    public void testNodRestrictContended2() {
        v2++;
    }

    @Benchmark
    @Group(value = "noFlag")
    @Fork(value = 1)
    public void testNoFlag1() {
        v1++;
    }

    @Benchmark
    @Group(value = "noFlag")
    @Fork(value = 1)
    public void testNoFlag2() {
        v2++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(RestrictContendedTest.class);
    }
}
