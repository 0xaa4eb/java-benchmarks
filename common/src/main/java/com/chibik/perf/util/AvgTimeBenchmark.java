package com.chibik.perf.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AvgTimeBenchmark {

    TimeUnit timeUnit() default TimeUnit.NANOSECONDS;

    int warmupIterations() default 10;

    int iterations() default 10;
}
