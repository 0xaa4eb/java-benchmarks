package com.chibik.perf.util;

public class Score {

    private final double val;
    private final ScoreTimeUnit unit;

    public Score(double val, ScoreTimeUnit unit) {
        this.val = val;
        this.unit = unit;
    }

    public double getVal() {
        return val;
    }

    public ScoreTimeUnit getUnit() {
        return unit;
    }

    public Score multiply(int multipler) {
        return new Score(val * multipler, unit);
    }

    public Score divide(int multipler) {
        return new Score(val / multipler, unit);
    }
}
