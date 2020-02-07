package com.chibik.perf.asm.obj;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class WriteToObject {

    public static class TestClass {

        public int r0;
        public int r1;
        public int r2;
        public int r3;
        public int r4;
        public int r5;
        public int r6;
        public int r7;
        public int r8;
        public int r9;
        public int r10;
        public int r11;
        public int r12;
        public int r13;
        public int r14;
        public int r15;
        public int r16;
        public int r17;
        public int r18;
        public int r19;
        public int r20;
    }

    private TestClass tc;

    @Setup(Level.Iteration)
    public void setUp() {
        tc = new TestClass();
    }

    @Benchmark
    public void storeR0() {
        tc.r0 = 0xdeadbeef;
    }

    @Benchmark
    public void storeToR20() {
        tc.r20 = 0xdeadbeef;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(WriteToObject.class);
    }
}
