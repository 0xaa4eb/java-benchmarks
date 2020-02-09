package com.chibik.perf.util;

import org.openjdk.jmh.annotations.Mode;

import java.util.List;

import static java.util.Arrays.asList;

public enum ScoreTimeUnit {

    SECONDS(asList("s/op", "ops/s"), 1_000_000_000),
    MILLISECONDS(asList("ms/op", "ops/ms"), 1_000_000),
    MICROSECONDS(asList("us/op", "ops/us"), 1_000),
    NANOSECONDS(asList("ns/op", "ops/ns"), 1);

    private final List<String> values;
    private final double ns;

    ScoreTimeUnit(List<String> values, double ns) {
        this.values = values;
        this.ns = ns;
    }

    public static ScoreTimeUnit byTimeUnit(String text) {
        text = text.trim();

        for (ScoreTimeUnit v : values()) {
            if (v.values.contains(text)) {
                return v;
            }
        }

        throw new RuntimeException("Unsupported time unit " + text);
    }

    public String getValue(Mode mode) {
        if (mode == Mode.Throughput) {
            return values.get(1);
        } else {
            return values.get(0);
        }
    }

    public List<String> getValues() {
        return values;
    }

    public double getNs() {
        return ns;
    }

    public static Score convertToReadable(Score score) {
        double current = score.getVal();

        for (ScoreTimeUnit v : values()) {
            double multiplier = score.getUnit().ns / v.ns;
            double converted = multiplier * current;
            if (converted >= 1 && converted <= 999.999) {
                return new Score(converted, v);
            }
        }

        double multiplier = score.getUnit().ns / NANOSECONDS.ns;
        double converted = multiplier * current;
        return new Score(converted, NANOSECONDS);
    }
}
