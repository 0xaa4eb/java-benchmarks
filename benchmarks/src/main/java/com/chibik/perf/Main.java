package com.chibik.perf;

import org.openjdk.jmh.results.RunResult;

import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        Collection<RunResult> results = new BenchmarkRunner().run("com.chibik.perf.collections.array");

        BenchmarkReportGenerator reportGenerator = new BenchmarkReportGenerator(results);
        reportGenerator.build(System.out);
    }
}
