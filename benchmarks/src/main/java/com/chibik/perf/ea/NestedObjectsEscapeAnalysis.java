package com.chibik.perf.ea;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import org.openjdk.jmh.annotations.*;

class A {
    final B value;

    A(B value) {
        this.value = value;
    }
}

class B {
    final C value;

    B(C value) {
        this.value = value;
    }
}

class C {

}

@Comment("Escape analysis can optimize nested allocations new A(new B(new C())) since dec 2021")
@AvgTimeBenchmark
@State(Scope.Thread)
public class NestedObjectsEscapeAnalysis {

    @Benchmark
    public void nestedAllocationWithNoGarbage() {
        /*
        * No garbage is allocated, only available sice dec 2021 builds: https://github.com/openjdk/jdk/commit/a1dfe57249db15c0c05d33a0014ac914a7093089
        */
        A z = new A(new B(new C()));
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(NestedObjectsEscapeAnalysis.class);
    }
}
