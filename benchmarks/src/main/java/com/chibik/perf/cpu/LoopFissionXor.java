package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@AvgTimeBenchmark
@State(Scope.Benchmark)
@Comment("Loop fission optimization. https://richardstartin.github.io/posts/loop-fission")
public class LoopFissionXor {

    @Param({"256", "1024", "4096"})
    int size;

    private long[] left;
    private long[] right;

    @Setup(Level.Trial)
    public void setup() {
        left = new long[size];
        right = new long[size];
        for (int i = 0; i < size; i++) {
            left[i] = ThreadLocalRandom.current().nextLong();
            right[i] = ThreadLocalRandom.current().nextLong();
        }
    }


    @Benchmark
    public int fused() {
        int count = 0;
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
            count += Long.bitCount(left[i]);
        }
        return count;
    }

    /*
    Why is the fissured loop faster? This is because of the loop which performs the XOR operation:
    for (int i = 0; i < left.length & i < right.length; i++) {
      left[i] ^= right[i];
    }
    This loop can be autovectorised - many XORs can be computed in a single instruction.
    */
    @Benchmark
    public int fissured() {
        for (int i = 0; i < left.length & i < right.length; i++) {
            left[i] ^= right[i];
        }
        int count = 0;
        for (long l : left) {
            count += Long.bitCount(l);
        }
        return count;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(LoopFissionXor.class);
    }
}
