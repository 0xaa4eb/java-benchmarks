package com.chibik.perf.collections.other;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.BitSet;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("Count all '1' bits")
public class BitSetVsByteArray {

    public static final int NBITS = 1_000_000;

    BitSet bs = new BitSet(NBITS);

    byte[] bytes = new byte[100000000];

    {
        for (int i = 0; i + i < NBITS; i++) {
            bs.set(i);
            bytes[i] = 1;
        }
    }

    @Benchmark
    @Comment("Count 1 bits using bit set")
    public int bitSet() {
        int count = 0;
        for (int i = 0; (i = bs.nextSetBit(i + 1)) >= 0; ) {
            count += i;
        }
        return count;
    }

    @Benchmark
    @Comment("Count 1 bits using byte[]")
    public int bytesSet() {
        int count = 0;
        for (int i = 0, bytesLength = bytes.length; i < bytesLength; i++) {
            byte b = bytes[i];
            if (b == 1)
                count += i;
        }
        return count;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(BitSetVsByteArray.class);
    }
}
