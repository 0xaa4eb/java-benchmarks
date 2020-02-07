package com.chibik.perf.concurrency.other;

import com.chibik.perf.BenchmarkRunner;
import net.openhft.affinity.Affinity;
import org.openjdk.jmh.annotations.*;
import util.Contended;

/*
Benchmark                                                  Mode  Cnt      Score      Error  Units
AffinityFlaseSharingTest.differentCpu                        ss   10  95157.342 ± 3456.773  us/op
AffinityFlaseSharingTest.differentCpu:iterateCpu0$$          ss   10  94472.676 ± 3767.672  us/op
AffinityFlaseSharingTest.differentCpu:iterateCpu1$$          ss   10  95842.008 ± 3153.448  us/op
AffinityFlaseSharingTest.differentCpu:·asm                   ss             NaN               ---
AffinityFlaseSharingTest.differentCpu2                       ss   10  89355.469 ± 2422.232  us/op
AffinityFlaseSharingTest.differentCpu2:iterateCpu2$$         ss   10  90131.709 ± 2327.432  us/op
AffinityFlaseSharingTest.differentCpu2:iterateCpu3$$         ss   10  88579.228 ± 2520.145  us/op
AffinityFlaseSharingTest.differentCpu2:·asm                  ss             NaN               ---
AffinityFlaseSharingTest.noAffinity                          ss   10  86777.223 ± 7211.526  us/op
AffinityFlaseSharingTest.noAffinity:iterateCpuNoAffinity     ss   10  86916.150 ± 7210.818  us/op
AffinityFlaseSharingTest.noAffinity:iterateCpuNoAffinity2    ss   10  86638.296 ± 7275.314  us/op
AffinityFlaseSharingTest.noAffinity:·asm                     ss             NaN               ---
AffinityFlaseSharingTest.sameCpu                             ss   10  13986.464 ± 4641.698  us/op
AffinityFlaseSharingTest.sameCpu:iterateCpu0                 ss   10  16837.815 ± 9141.284  us/op
AffinityFlaseSharingTest.sameCpu:iterateCpu0$                ss   10  11135.114 ±  419.960  us/op
AffinityFlaseSharingTest.sameCpu:·asm                        ss             NaN               ---
AffinityFlaseSharingTest.sameCpu2                            ss   10  13511.208 ± 4530.644  us/op
AffinityFlaseSharingTest.sameCpu2:iterateCpu3                ss   10  15846.600 ± 8913.489  us/op
AffinityFlaseSharingTest.sameCpu2:iterateCpu3$               ss   10  11175.816 ±  334.502  us/op
AffinityFlaseSharingTest.sameCpu2:·asm                       ss             NaN               ---
*/

@State(Scope.Group)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class AffinityFlaseSharingTest {

    @Contended
    private volatile long v1;
    @Contended
    private volatile long v2;

    @Benchmark
    @Group(value = "noAffinity")
    public void iterateCpuNoAffinity() {
        for(int i = 0; i < 2_000_000; i++) {
            v1++;
        }
    }

    @Benchmark
    @Group(value = "noAffinity")
    public void iterateCpuNoAffinity2() {
        for(int i = 0; i < 2_000_000; i++) {
            v2++;
        }
    }

    @Benchmark
    @Group(value = "sameCpu")
    public void iterateCpu0() {
        Affinity.setAffinity(0);
        for(int i = 0; i < 2_000_000; i++) {
            v1++;
        }
    }

    @Benchmark
    @Group(value = "sameCpu")
    public void iterateCpu0$() {
        Affinity.setAffinity(0);
        for(int i = 0; i < 2_000_000; i++) {
            v2++;
        }
    }

    @Benchmark
    @Group(value = "sameCpu2")
    public void iterateCpu3() {
        Affinity.setAffinity(3);
        for(int i = 0; i < 2_000_000; i++) {
            v1++;
        }
    }

    @Benchmark
    @Group(value = "sameCpu2")
    public void iterateCpu3$() {
        Affinity.setAffinity(3);
        for(int i = 0; i < 2_000_000; i++) {
            v2++;
        }
    }

    @Benchmark
    @Group(value = "differentCpu")
    public void iterateCpu0$$() {
        Affinity.setAffinity(0);
        for(int i = 0; i < 2_000_000; i++) {
            v1++;
        }
    }

    @Benchmark
    @Group(value = "differentCpu")
    public void iterateCpu1$$() {
        Affinity.setAffinity(1);
        for(int i = 0; i < 2_000_000; i++) {
            v2++;
        }
    }

    @Benchmark
    @Group(value = "differentCpu2")
    public void iterateCpu2$$() {
        Affinity.setAffinity(3);
        for(int i = 0; i < 2_000_000; i++) {
            v1++;
        }
    }

    @Benchmark
    @Group(value = "differentCpu2")
    public void iterateCpu3$$() {
        Affinity.setAffinity(2);
        for(int i = 0; i < 2_000_000; i++) {
            v2++;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(AffinityFlaseSharingTest.class);
    }
}
