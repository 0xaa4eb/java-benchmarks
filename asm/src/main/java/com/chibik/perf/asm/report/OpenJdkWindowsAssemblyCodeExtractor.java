package com.chibik.perf.asm.report;

import com.chibik.perf.report.ReportBuildException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenJdkWindowsAssemblyCodeExtractor {

    private static final String C2_COMPILED_METHOD_HEADER = "============================= C2-compiled nmethod ==============================";
    private static final String C1_COMPILED_METHOD_HEADER = "============================= C1-compiled nmethod ==============================";
    private static final Pattern REGEX = Pattern.compile("Compiled method \\(c[12]\\)[\\s\\d]+([a-zA-Z0-9.]+)::(.+) \\(\\d+ bytes\\)", Pattern.DOTALL);
    private static final String VERIFIED_ENTRY_POINT = "[Verified Entry Point]";
    private static final String ASSEMBLY_METHOD_END = "--------------------------------------------------------------------------------";

    private final String systemOutText;

    public OpenJdkWindowsAssemblyCodeExtractor(String out) {
        this.systemOutText = out;
    }

    public List<AssemblyCode> extractC1Compiled() {
        return extract(C1_COMPILED_METHOD_HEADER);
    }

    public List<AssemblyCode> extractC2Compiled() {
        return extract(C2_COMPILED_METHOD_HEADER);
    }

    private List<AssemblyCode> extract(String compiledMethodHeader) {
        List<AssemblyCode> codes = new ArrayList<>();
        int index = -1;
        while ((index = systemOutText.indexOf(compiledMethodHeader, index + 1)) > 0) {
            int from = systemOutText.indexOf(VERIFIED_ENTRY_POINT, index + compiledMethodHeader.length());

            String header = systemOutText.substring(index, from);
            Matcher matcher = REGEX.matcher(header);
            if (!matcher.find()) {
                throw new ReportBuildException("Could not find class and method by regexp " + REGEX + " in header " + header);
            }

            int to = systemOutText.indexOf(ASSEMBLY_METHOD_END, from);
            codes.add(new AssemblyCode(matcher.group(1), matcher.group(2), systemOutText.substring(from + VERIFIED_ENTRY_POINT.length() + 1, to)));
            index = to;
        }
        return codes;
    }
}
