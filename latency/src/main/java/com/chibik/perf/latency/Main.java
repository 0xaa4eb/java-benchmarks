package com.chibik.perf.latency;

import com.chibik.perf.BenchmarkReportGenerator;
import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.results.RunResult;

import java.io.*;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws IOException {
        try (OutputStream target = new BufferedOutputStream(new FileOutputStream("report.txt"))) {
            runForPackage("com.chibik.perf.latency", target);
        }
    }

    private static void runForPackage(String packageName, OutputStream target) {
        Collection<RunResult> results = new BenchmarkRunner().run(packageName);
        BenchmarkReportGenerator reportGenerator = new BenchmarkReportGenerator(results);
        reportGenerator.build(target);
    }
}
