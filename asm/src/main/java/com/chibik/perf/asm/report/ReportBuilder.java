package com.chibik.perf.asm.report;

import com.chibik.perf.report.HTMLTableBuilder;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportBuilder {

    public void build(String systemOutOutput, OutputStream targetStream) {
        HTMLTableBuilder tableBuilder = new HTMLTableBuilder(new HTMLTableBuilder.Props().caption("Codes"), "Java code", "Assembly code");
        OpenJdkWindowsAssemblyCodeExtractor extractor = new OpenJdkWindowsAssemblyCodeExtractor(systemOutOutput);

        var sourceFileReader = new SourceFileReader();

        Set<MethodName> processedMethods = new HashSet<>();

        List<AssemblyCode> codes = new ArrayList<>();
        codes.addAll(extractor.extractC2Compiled());
        codes.addAll(extractor.extractC1Compiled());

        for (AssemblyCode code : codes) {
            if (!processedMethods.add(code.getMethod())) {
                continue;
            }

            ClassFileSource source = sourceFileReader.get(code.getClassName());
            String benchmarkMethodSource = new ClassFileParser().findBenchmarkMethod(source, code.getMethodName());

            tableBuilder.addRowValues(benchmarkMethodSource, code.getCode());
        }

        System.out.println(tableBuilder.build());
    }
}
