package com.chibik.perf.date;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import org.openjdk.jmh.annotations.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(value = {Mode.AverageTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@IncludedInReport
public class DateVsLocalDateTimeConstructor {

    private Date d;

    private LocalDateTime ldt;

    private long res;

    @Benchmark
    @Comment("new Date();")
    public void newDate() {
        d = new Date();
    }

    @Benchmark
    @Comment("LocalDateTime.now();")
    public void createLocalDateTime() {
        ldt = LocalDateTime.now();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        res += d != null ? d.getTime() : 0;
        res += ldt != null ? ldt.getNano() : 0;
        assert res != 0;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(DateVsLocalDateTimeConstructor.class);
    }
}
