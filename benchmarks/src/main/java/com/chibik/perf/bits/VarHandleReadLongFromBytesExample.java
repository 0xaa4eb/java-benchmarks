package com.chibik.perf.bits;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.util.concurrent.ThreadLocalRandom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;

/*
* https://minborgsjavapot.blogspot.com/2023/01/java-21-performance-improvements.html
*/
@AvgTimeBenchmark(iterations = 5, warmupIterations = 5)
@State(Scope.Thread)
public class VarHandleReadLongFromBytesExample {

    private static final VarHandle LONG = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);

    static long getLong(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL)      ) +
            ((b[off + 6] & 0xFFL) <<  8) +
            ((b[off + 5] & 0xFFL) << 16) +
            ((b[off + 4] & 0xFFL) << 24) +
            ((b[off + 3] & 0xFFL) << 32) +
            ((b[off + 2] & 0xFFL) << 40) +
            ((b[off + 1] & 0xFFL) << 48) +
            (((long) b[off])      << 56);
    }

    private byte[] data;

    @Setup(Level.Iteration)
    public void setUp() {
        data = new byte[1024];
        ThreadLocalRandom.current().nextBytes(data);
    }

    @Benchmark
    public long varHandleImplementation() {
        return (long) LONG.get(data, 32);
    }

    @Benchmark
    public long bitManipulationImplementation() {
        return getLong(data, 32);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(VarHandleReadLongFromBytesExample.class);
    }
}
