package com.chibik.perf.asm.intrinsics;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@PrintAssembly
public class GetClass {

    private Object field;

    @Setup(Level.Iteration)
    public void setUp() {
        int v = ThreadLocalRandom.current().nextInt(3);
        if (v == 0) {
            field = new Object();
        } else if (v == 1) {
            field = "asdasdsad";
        } else {
            field = 5;
        }
    }

    @Benchmark
    @Comment("field.getClass()")
    public Class<?> getClassMethod() {
        return field.getClass();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(GetClass.class);
    }
}
