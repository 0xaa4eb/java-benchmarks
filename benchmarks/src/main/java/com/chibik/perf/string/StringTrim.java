package com.chibik.perf.string;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@AvgTimeBenchmark
public class StringTrim {

    @Param({"  abcdf ", "  ", "abcdf"})
    private String test;

    @Benchmark
    public String testStringTrimAvgTime() {
        return test.trim();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(StringTrim.class);
    }
}
