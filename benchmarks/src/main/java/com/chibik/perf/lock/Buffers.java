package com.chibik.perf.lock;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class Buffers {

    @Param({"1000", "1000000"})
    private int size;

    byte[] array;
    File file;

    @Setup
    public void setup() throws IOException {
        array = new byte[size];
        new Random().nextBytes(array);
        Path p = Files.createTempFile("buffers", "test");
        Files.write(p, array);
        file = p.toFile();

        if (false) {
            // Sanity checks
            try (InputStream yolobis = new YOLOBufferedInputStream(new ByteArrayInputStream(array));
                 InputStream bis = new BufferedInputStream(new ByteArrayInputStream(array))) {
                for (int c = 0; c < size; c++) {
                    if (yolobis.read() != bis.read()) {
                        throw new IllegalStateException("Wrong.");
                    }
                }
                if (yolobis.read() != bis.read()) {
                    throw new IllegalStateException("Wrong at end");
                }
            }
        }
    }

    private void readOut(Blackhole bh, InputStream s) throws IOException {
        int b;
        while ((b = s.read()) != -1) {
            bh.consume(b);
        }
    }

    @Benchmark
    public void fis(Blackhole bh) throws IOException {
        try (FileInputStream s = new FileInputStream(file)) {
            readOut(bh, s);
        }
    }

    @Benchmark
    public void fis_bis(Blackhole bh) throws IOException {
        try (InputStream s = new BufferedInputStream(new FileInputStream(file))) {
            readOut(bh, s);
        }
    }

    @Benchmark
    public void fis_yolobis(Blackhole bh) throws IOException {
        try (InputStream s = new YOLOBufferedInputStream(new FileInputStream(file))) {
            readOut(bh, s);
        }
    }

    @Benchmark
    public void bais(Blackhole bh) throws IOException {
        try (ByteArrayInputStream s = new ByteArrayInputStream(array)) {
            readOut(bh, s);
        }
    }

    @Benchmark
    public void bais_bis(Blackhole bh) throws IOException {
        try (InputStream s = new BufferedInputStream(new ByteArrayInputStream(array))) {
            readOut(bh, s);
        }
    }

    @Benchmark
    public void bais_yolobis(Blackhole bh) throws IOException {
        try (BufferedInputStream s = new YOLOBufferedInputStream(new ByteArrayInputStream(array))) {
            readOut(bh, s);
        }
    }

    /**
     * @author Aleksey Shipilëv, YOLO Engineering, LLC.
     */
    private static class YOLOBufferedInputStream extends BufferedInputStream {
        public YOLOBufferedInputStream(InputStream in) {
            super(in);
        }

        @Override
        public int read() throws IOException {
            if (pos >= count) {
                // Let superclass replenish the buffer
                return super.read();
            }
            return buf[pos++] & 0xFF;
        }
    }

    /*
        Slow streams:
            Buffering is still very beneficial, but relative improvement would be worse without biased locking.
            Avoiding locking gives comparable performance.
            # ---- JDK 17, +BiasedLocking
            Buffers.fis              1000  avgt   10     319.211 ±    2.068  us/op
            Buffers.fis_bis          1000  avgt   10       5.855 ±    0.027  us/op
            Buffers.fis_yolobis      1000  avgt   10       5.847 ±    0.025  us/op  ; No improvement, expected
            Buffers.fis           1000000  avgt   10  319708.538 ± 4055.425  us/op
            Buffers.fis_bis       1000000  avgt   10    2861.788 ±    9.739  us/op
            Buffers.fis_yolobis   1000000  avgt   10    2420.323 ±    7.029  us/op  ; Some improvement, code gen quality
            # ---- JDK 17, -BiasedLocking
            Buffers.fis              1000  avgt   10     316.564 ±    2.085  us/op
            Buffers.fis_bis          1000  avgt   10      13.120 ±    0.059  us/op  ; FIS is slow, BIS is faster even with locking
            Buffers.fis_yolobis      1000  avgt   10       5.837 ±    0.033  us/op  ; Avoids locking on fast path
            Buffers.fis           1000000  avgt   10  317954.162 ± 3007.230  us/op
            Buffers.fis_bis       1000000  avgt   10    9733.030 ±    8.331  us/op
            Buffers.fis_yolobis   1000000  avgt   10    2410.321 ±    4.040  us/op  ; Avoids locking on fast path
            # ----  JDK 19 (no biased locking support)
            Buffers.fis              1000  avgt   10     310.152 ±    2.300  us/op
            Buffers.fis_bis          1000  avgt   10      13.626 ±    0.032  us/op
            Buffers.fis_yolobis      1000  avgt   10       5.771 ±    0.028  us/op
            Buffers.fis           1000000  avgt   10  313669.547 ± 2967.343  us/op
            Buffers.fis_bis       1000000  avgt   10   10253.125 ±   13.085  us/op
            Buffers.fis_yolobis   1000000  avgt   10    2413.215 ±    9.465  us/op
        Fast streams:
            Buffering is not that great idea to begin with, but it would get even worse without biased locking.
            Avoiding locking gives comparable performance.
            # ---- JDK 17, +BiasedLocking
            Buffers.bais             1000  avgt   10       0.548 ±    0.001  us/op
            Buffers.bais_bis         1000  avgt   10       2.783 ±    0.006  us/op
            Buffers.bais_yolobis     1000  avgt   10       2.794 ±    0.007  us/op  ; No improvement, expected
            Buffers.bais          1000000  avgt   10    2482.329 ±    6.610  us/op
            Buffers.bais_bis      1000000  avgt   10    2730.665 ±    3.409  us/op
            Buffers.bais_yolobis  1000000  avgt   10    2293.425 ±    7.657  us/op  ; Some improvement, code gen quality
            # ---- JDK 17, -BiasedLocking
            Buffers.bais             1000  avgt   10       0.548 ±    0.001  us/op
            Buffers.bais_bis         1000  avgt   10      10.225 ±    0.015  us/op  ; Taking locks in BAIS and BIS
            Buffers.bais_yolobis     1000  avgt   10       2.726 ±    0.005  us/op  ; Avoids locking on fast path, still does additional work
            Buffers.bais          1000000  avgt   10    9585.825 ±    5.910  us/op  ; Taking lock in BAIS (lock elimination does not work)
            Buffers.bais_bis      1000000  avgt   10    9610.271 ±    9.220  us/op  ; Taking two locks, one in BAIS, one in BIS
            Buffers.bais_yolobis  1000000  avgt   10    2302.527 ±    7.792  us/op  ; Avoids locking on fast path
            # ----  JDK 19 (no biased locking support)
            Buffers.bais             1000  avgt   10       0.548 ±    0.001  us/op
            Buffers.bais_bis         1000  avgt   10      10.680 ±    0.012  us/op  ; A bit slower than JDK 17, synchronized -> ReentrantLock
            Buffers.bais_yolobis     1000  avgt   10       2.728 ±    0.005  us/op
            Buffers.bais          1000000  avgt   10    9585.592 ±    5.097  us/op
            Buffers.bais_bis      1000000  avgt   10   10134.355 ±   13.687  us/op  ; A bit slower than JDK 17, synchronized -> ReentrantLock
            Buffers.bais_yolobis  1000000  avgt   10    2293.871 ±    0.628  us/op
     */
}