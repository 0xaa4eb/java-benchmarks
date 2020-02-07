package com.chibik.perf.asm.report;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClassFileParserTest {

    @Test
    public void testParsingSimpleCase() {
        String benchmarkExample =
                "package com.chibik.perf.asm.method;\n" +
                "\n" +
                "import com.chibik.perf.BenchmarkRunner;\n" +
                "import com.chibik.perf.util.PrintAssembly;\n" +
                "import org.openjdk.jmh.annotations.*;\n" +
                "\n" +
                "@State(Scope.Benchmark)\n" +
                "@PrintAssembly\n" +
                "public class EmptyMethod {\n" +
                "\n" +
                "    @Setup(Level.Iteration)\n" +
                "    public void setUp() {\n" +
                "    }\n" +
                "\n" +
                "    @Benchmark\n" +
                "    public void emptyMethod() {\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @Benchmark\n" +
                "    public long returnDeadBeef() {\n" +
                "        return 0xdeadbeef;\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        BenchmarkRunner.run(EmptyMethod.class);\n" +
                "    }\n" +
                "}\n";

        ClassFileSource classFileSource = new ClassFileSource("com.chibik.perf.asm.method.EmptyMethod", benchmarkExample);
        List<String> methodSources = new ClassFileParser().extractBenchmarkMethods(classFileSource);

        assertEquals(2, methodSources.size());

        assertEquals(
                "    @Benchmark\n" +
                        "    public void emptyMethod() {\n" +
                        "\n" +
                        "    }",
                methodSources.get(0)
        );
        assertEquals(
                "    @Benchmark\n" +
                        "    public long returnDeadBeef() {\n" +
                        "        return 0xdeadbeef;\n" +
                        "    }",
                methodSources.get(1)
        );
    }

    @Test
    public void testParsingAnotherCase() {
        String benchmarkExample =
                "package com.chibik.perf.asm.constfold;\n" +
                        "\n" +
                        "import com.chibik.perf.BenchmarkRunner;\n" +
                        "import com.chibik.perf.util.PrintAssembly;\n" +
                        "import org.openjdk.jmh.annotations.*;\n" +
                        "\n" +
                        "@State(Scope.Benchmark)\n" +
                        "@PrintAssembly\n" +
                        "public class StringConstandFolding {\n" +
                        "\n" +
                        "    private static final String s = \"asdfgdshjffasd\";\n" +
                        "\n" +
                        "    @Setup(Level.Iteration)\n" +
                        "    public void setUp() {\n" +
                        "    }\n" +
                        "\n" +
                        "    @Benchmark\n" +
                        "    public int returnStringLen() {\n" +
                        "        return s.length();\n" +
                        "    }\n" +
                        "\n" +
                        "    @Benchmark\n" +
                        "    public String returnStringItself() {\n" +
                        "        return s;\n" +
                        "    }\n" +
                        "\n" +
                        "    public static void main(String[] args) {\n" +
                        "        BenchmarkRunner.run(StringConstandFolding.class);\n" +
                        "    }\n" +
                        "}\n";

        ClassFileSource classFileSource = new ClassFileSource("com.chibik.perf.asm.constfold.StringConstandFolding", benchmarkExample);
        List<String> methodSources = new ClassFileParser().extractBenchmarkMethods(classFileSource);

        assertEquals(2, methodSources.size());

        assertEquals(
                "    @Benchmark\n" +
                        "    public String returnStringItself() {\n" +
                        "        return s;\n" +
                        "    }",
                methodSources.get(1)
        );
    }
}