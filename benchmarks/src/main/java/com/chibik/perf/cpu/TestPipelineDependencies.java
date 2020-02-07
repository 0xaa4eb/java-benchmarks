package com.chibik.perf.cpu;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@AvgTimeBenchmark
@Comment("Add 1 to variables with or without dependencies")
public class TestPipelineDependencies {

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;

    @Benchmark
    @Comment("a = 1; b = a + 1; .... dependencies")
    public int computeWithDependencies() {
        a = 1;
        b = a + 1;
        c = b + 1;
        d = c + 1;
        return d;
    }

    @Benchmark
    @Comment("a = 1; e = b + 1; .... dependencies")
    public int computeWithNoDependencies() {
        a = 1;
        e = b + 1;
        f = c + 1;
        g = d + 1;
        return g;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(TestPipelineDependencies.class);
    }
}
