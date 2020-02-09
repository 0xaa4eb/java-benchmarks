package com.chibik.perf.string;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.AvgTimeBenchmark;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(Scope.Benchmark)
@AvgTimeBenchmark
public class ReplaceAllVsPatternReplace {

    @Param("ABCDEFGH")
    private String str;

    @Param("DEF")
    private String replaceWhat;

    @Param("DEA")
    private String replaceTo;

    private String result;

    private Pattern pattern;

    private StringBuilder builder = new StringBuilder(1000);

    @Setup(Level.Iteration)
    public void setUp() {
        pattern = Pattern.compile(replaceWhat);
    }

    @TearDown(Level.Iteration)
    public void validate() {
        if (!"ABCDEAGH".equals(result)) {
            throw new RuntimeException(result);
        }
    }

    @Benchmark
    public String testReplaceAll() {
        result = str.replaceAll(replaceWhat, replaceTo);
        return result;
    }

    @Benchmark
    public String testReplaceWithPattern() {
        Matcher matcher = pattern.matcher(str);
        result = matcher.replaceAll(replaceTo);
        return result;
    }

    @Benchmark
    public String testReplaceWithIndexOf() {
        int index = str.indexOf(replaceWhat);
        if (index != -1) {
            result = str.substring(0, index) + replaceTo + str.substring(index + 3);
        }
        return result;
    }

    @Benchmark
    public String testReplaceWithIndexOfWithReusabelBuilder() {
        builder.setLength(0);
        int index = str.indexOf(replaceWhat);
        builder.append(str, 0, index);
        builder.append(replaceTo, 0, 3);
        builder.append(str, index + 3, str.length());
        result = builder.toString();
        return result;
    }

    public static void main(String[] args) {

        BenchmarkRunner.run(ReplaceAllVsPatternReplace.class);
    }
}
