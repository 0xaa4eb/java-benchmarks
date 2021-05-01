package com.chibik.perf;

import org.openjdk.jmh.results.RunResult;

import java.io.*;
import java.util.Collection;

public class Main {

    public static void main(String[] args) throws IOException {
        try (OutputStream target = new BufferedOutputStream(new FileOutputStream("report.txt"))) {
            runForPackage("com.chibik.perf.collections.list", target);
/*            runForPackage("com.chibik.perf.concurrency", target);
            runForPackage("com.chibik.perf.cpu", target);*/
        }
    }

    private static void runForPackage(String packageName, OutputStream target) {
        Collection<RunResult> results = new BenchmarkRunner().run(packageName);
        BenchmarkReportGenerator reportGenerator = new BenchmarkReportGenerator(results);
        reportGenerator.build(target);
    }
}
