package com.chibik.perf.asm;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.asm.report.ConsoleUtil;
import com.chibik.perf.asm.report.ReportBuilder;

public class Main {

    public static void main(String[] args) {
        String output = ConsoleUtil.executeAndCaptureConsoleOutput(() -> new BenchmarkRunner().run("com.chibik.perf.asm"));
        new ReportBuilder().build(output, System.out);
    }
}
