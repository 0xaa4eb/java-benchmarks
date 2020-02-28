package com.chibik.perf.asm.constfold;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class StringConstandFolding {

    private static final String s = "asdfgdshjffasd";

    @Setup(Level.Iteration)
    public void setUp() {
    }

    @Benchmark
    public int returnStringLen() {
        return s.length();
    }

    @Benchmark
    public String returnStringItself() {
        return s;
    }

    @Benchmark
    public int returnHashCode() {
        return s.hashCode();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(StringConstandFolding.class);
    }
}
