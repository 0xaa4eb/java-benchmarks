package com.chibik.perf.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleSnapshotBenchmark {

    int batchSize() default 10000;

    int iterations() default 20;

    TimeUnit timeUnit() default TimeUnit.NANOSECONDS;
}
