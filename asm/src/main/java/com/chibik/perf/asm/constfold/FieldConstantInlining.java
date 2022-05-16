package com.chibik.perf.asm.constfold;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.PrintAssembly;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@PrintAssembly
public class FieldConstantInlining {

    private final long field = 0xdeadbeef;

    @Setup(Level.Iteration)
    public void setUp() {
    }

    /*
    0x00000268a50cf9cc:   movabs rax,0xffffffffdeadbeef
    0x00000268a50cf9d6:   add    rsp,0x30
    0x00000268a50cf9da:   pop    rbp
    0x00000268a50cf9db:   cmp    rsp,QWORD PTR [r15+0x340]    ;   {poll_return}
    0x00000268a50cf9e2:   ja     0x00000268a50cf9e9
    0x00000268a50cf9e8:   ret
    */
    @Benchmark
    public long readField() {
        return field;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(FieldConstantInlining.class);
    }
}
