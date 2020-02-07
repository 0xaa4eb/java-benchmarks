package com.chibik.perf.asm.report;

public class ClassFileSource {

    private final String className;
    private final String sourceText;

    public ClassFileSource(String className, String sourceText) {
        this.className = className;
        this.sourceText = sourceText;
    }

    public String getClassName() {
        return className;
    }

    public String getSourceText() {
        return sourceText;
    }
}
