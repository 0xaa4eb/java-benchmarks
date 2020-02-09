package com.chibik.perf.asm.report;

import com.chibik.perf.report.ReportBuildException;

import java.util.ArrayList;
import java.util.List;

public class ClassFileParser {

    private static final String BENCHMARK_ANNOTATION = "@Benchmark";

    public String findBenchmarkMethod(ClassFileSource classFileSource, String methodName) {
        return extractBenchmarkMethods(classFileSource).stream().filter(method -> method.contains(methodName))
                .findFirst().orElseThrow(
                        () -> new ReportBuildException("Method with name " + methodName + " not found in class " + classFileSource.getClassName())
                );
    }

    public List<String> extractBenchmarkMethods(ClassFileSource classFileSource) {
        String sourceText = classFileSource.getSourceText();
        List<String> methods = new ArrayList<>();

        int index = -1;
        while ((index = sourceText.indexOf(BENCHMARK_ANNOTATION, index + 1)) > 0) {

            int next = sourceText.indexOf(BENCHMARK_ANNOTATION, index + BENCHMARK_ANNOTATION.length());
            if (next < 0) {
                // Search for an empty string, for now it's enough to handle most cases
                next = sourceText.indexOf("\n\n", index + BENCHMARK_ANNOTATION.length());
                if (next < 0) {
                    next = sourceText.length();
                }
            }
            methods.add("    " + classFileSource.getSourceText().substring(index, next).trim());
        }

        return methods;
    }
}
