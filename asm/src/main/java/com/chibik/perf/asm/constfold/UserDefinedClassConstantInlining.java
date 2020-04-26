package com.chibik.perf.asm.constfold;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class UserDefinedClassConstantInlining {

    private static final UserDefinedClass staticFinalRef = new UserDefinedClass(0xdeadbeef);

    public static class UserDefinedClass {
        private final long value;

        public UserDefinedClass(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    @Setup(Level.Iteration)
    public void setUp() {
    }

    @Benchmark
    public long returnStringLen() {
        return staticFinalRef.getValue();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(UserDefinedClassConstantInlining.class);
    }
}
