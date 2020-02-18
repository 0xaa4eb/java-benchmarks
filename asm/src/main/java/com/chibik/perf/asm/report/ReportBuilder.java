package com.chibik.perf.asm.report;

import com.chibik.perf.report.HTMLTable;
import com.chibik.perf.report.HTMLText;
import com.chibik.perf.report.ReportBuildException;
import com.chibik.perf.util.PrintAssembly;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ReportBuilder {

    public void build(String systemOutOutput, OutputStream targetStream) {
        HTMLTable table = new HTMLTable(new HTMLTable.Props().caption("Codes"), Arrays.asList(HTMLText.of("Java code"), HTMLText.of("Assembly code")));
        OpenJdkWindowsAssemblyCodeExtractor extractor = new OpenJdkWindowsAssemblyCodeExtractor(systemOutOutput);

        var sourceFileReader = new SourceFileReader();

        Set<MethodName> processedMethods = new HashSet<>();

        List<AssemblyCode> codes = new ArrayList<>();
        codes.addAll(extractor.extractC2Compiled());
        codes.addAll(extractor.extractC1Compiled());

        codes.sort(Comparator.comparing(this::getComplexity));

        for (AssemblyCode code : codes) {
            if (!processedMethods.add(code.getMethod())) {
                continue;
            }

            ClassFileSource source = sourceFileReader.get(code.getClassName());
            String benchmarkMethodSource = new ClassFileParser().findBenchmarkMethod(source, code.getMethodName());

            table.addRowValues(Arrays.asList(HTMLText.of(benchmarkMethodSource), HTMLText.of(code.getCode())));
        }

        try {
            targetStream.write(table.render().getBytes());
        } catch (IOException e) {
            throw new ReportBuildException("Error while writing to stream", e);
        }
    }

    private int getComplexity(AssemblyCode code) {
        PrintAssembly annotation = null;
        try {
            annotation = Class.forName(code.getClassName()).getAnnotation(PrintAssembly.class);
        } catch (ClassNotFoundException e) {
            throw new ReportBuildException(e);
        }
        return annotation != null ? annotation.complexity() : 1;
    }
}
