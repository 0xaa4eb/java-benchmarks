package com.chibik.perf.asm.register;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;
import util.Contended;

@State(Scope.Benchmark)
@PrintAssembly
public class LocalVariableToRegister {

    @Param({ "32768" })
    int size;

    @Contended
    private int[] array;

    private int sum;

    @Setup(Level.Iteration)
    public void setUp() {
        array = new int[size];
        for(int i = 0; i < size; i++) {
            array[i] = i % 751;
        }

        sum = 0;
    }

    @TearDown(Level.Iteration)
    public void tearDownInv() {
        if(sum == 0) {
            throw new RuntimeException("Nothing is done, sum is 0");
        }
    }

    @Benchmark
    public void instanceVariable() {
        for(int i = 0; i < size; i++) {
            sum += array[i];
        }
    }

    @Benchmark
    public void localVariable() {
        int s = 0;
        for(int i = 0; i < size; i++) {
            s += array[i];
        }
        sum += s;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LocalVariableToRegister.class);
    }
}
