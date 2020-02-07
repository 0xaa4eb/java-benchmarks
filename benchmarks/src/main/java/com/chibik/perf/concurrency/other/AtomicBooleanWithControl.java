package com.chibik.perf.concurrency.other;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Control;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@State(Scope.Group)
public class AtomicBooleanWithControl {

    public final AtomicBoolean flag = new AtomicBoolean();

    @Benchmark
    @Group("pingpong")
    public void ping(Control cnt) {
        while (!cnt.stopMeasurement && !flag.compareAndSet(false, true)) {

        }
    }

    @Benchmark
    @Group("pingpong")
    public void pong(Control cnt) {
        while (!cnt.stopMeasurement && !flag.compareAndSet(true, false)) {

        }
    }

    @Benchmark
    @Group("pingpongNoControl")
    public void ping() {
        while (!flag.compareAndSet(false, true)) {

        }
    }

    @Benchmark
    @Group("pingpongNoControl")
    public void pong() {
        while (!flag.compareAndSet(true, false)) {

        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AtomicBooleanWithControl.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(5)
                .threads(2)
                .forks(1)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .build();

        new Runner(opt).run();
    }
}
