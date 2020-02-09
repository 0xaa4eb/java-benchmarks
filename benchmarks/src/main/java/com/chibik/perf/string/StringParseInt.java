package com.chibik.perf.string;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@AvgTimeBenchmark
public class StringParseInt {

    @Param({"2", "2055", "12342345"})
    private String source;

    @Benchmark
    public int parseIntAvgTime() {
        return Integer.parseInt(source);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(StringParseInt.class);
    }
}
