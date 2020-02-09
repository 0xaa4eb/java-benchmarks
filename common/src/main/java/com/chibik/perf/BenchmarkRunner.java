package com.chibik.perf;

import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.BenchmarkList;
import org.openjdk.jmh.runner.BenchmarkListEntry;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.format.OutputFormatFactory;
import org.openjdk.jmh.runner.options.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BenchmarkRunner {

    public static Collection<RunResult> runWithoutFork(Class<?> clazz) {
        return run(clazz, 0);
    }

    public static Collection<RunResult> run(Class<?> clazz) {
        return run(clazz, 1);
    }

    public static Collection<RunResult> run(Class<?> clazz, int forks) {
        try {
            List<String> forkJvmArgs = new ArrayList<>();

            forkJvmArgs.add("-XX:+UnlockDiagnosticVMOptions");

            PrintAssembly printAssembly = clazz.getAnnotation(PrintAssembly.class);
            if (printAssembly != null) {
                forkJvmArgs.add("-XX:CompileCommand=print,*" + clazz.getSimpleName() + ".*");
                forkJvmArgs.add("-XX:PrintAssemblyOptions=intel,hsdis-help");
                forkJvmArgs.add("-XX:-UseCompressedOops");
            }

            forkJvmArgs.add("-Xmx8G");
            forkJvmArgs.add("-ea");

            AdditionalForkJvmKeys additionalKeys = clazz.getAnnotation(AdditionalForkJvmKeys.class);

            if (additionalKeys != null) {
                forkJvmArgs.addAll(Arrays.asList(additionalKeys.value()));
            }

            ChainedOptionsBuilder optionsBuilder = new OptionsBuilder()
                    .include(clazz.getSimpleName())
                    .jvmArgsAppend(forkJvmArgs.toArray(new String[0]))
                    .forks(forks)
                    .addProfiler(GCProfiler.class);

            if (printAssembly != null) {
                optionsBuilder = optionsBuilder
                        .mode(Mode.AverageTime)
                        .timeUnit(TimeUnit.NANOSECONDS)
                        .warmupIterations(5)
                        .measurementIterations(5)
                        .warmupTime(TimeValue.seconds(1))
                        .measurementTime(TimeValue.seconds(1));
            }

            AvgTimeBenchmark avgTimeBench = clazz.getAnnotation(AvgTimeBenchmark.class);
            ThroughputBenchmark thrptAnnotation = clazz.getAnnotation(ThroughputBenchmark.class);
            if (avgTimeBench != null || thrptAnnotation != null) {
                optionsBuilder = optionsBuilder
                        .mode(avgTimeBench != null ? Mode.AverageTime : Mode.Throughput)
                        .warmupIterations(10)
                        .measurementIterations(10)
                        .warmupTime(TimeValue.seconds(1))
                        .measurementTime(TimeValue.seconds(1));
                if (avgTimeBench != null) {
                    optionsBuilder = optionsBuilder.timeUnit(avgTimeBench.timeUnit());
                } else {
                    optionsBuilder = optionsBuilder.timeUnit(thrptAnnotation.timeUnit());
                }
            }

            SingleShotBenchmark ssBench = clazz.getAnnotation(SingleShotBenchmark.class);
            if (ssBench != null) {
                optionsBuilder = optionsBuilder
                        .mode(Mode.SingleShotTime)
                        .warmupIterations(ssBench.iterations())
                        .measurementIterations(ssBench.iterations())
                        .measurementBatchSize(ssBench.batchSize())
                        .warmupBatchSize(ssBench.batchSize());
                optionsBuilder = optionsBuilder.timeUnit(ssBench.timeUnit());
            }

            Collection<RunResult> runResults = new Runner(optionsBuilder.build()).run();
            printResults(clazz, runResults);
            return runResults;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<RunResult> run() {
        return run("");
    }

    public Collection<RunResult> runWithoutFork(String packageName) {
        return run(packageName, BenchmarkRunner::runWithoutFork);
    }

    public Collection<RunResult> run(String packageName) {
        return run(packageName, BenchmarkRunner::run);
    }

    private Collection<RunResult> run(String packageName, Function<Class<?>, Collection<RunResult>> method) {
        try {

            Collection<RunResult> results = new ArrayList<>();
            BenchmarkList benchmarkList = BenchmarkList.defaultList();

            Set<BenchmarkListEntry> entities = benchmarkList.getAll(OutputFormatFactory.createFormatInstance(System.out, VerboseMode.NORMAL), Collections.emptyList());

            for (var entity : entities) {
                if (entity.getUserClassQName().startsWith(packageName)) {
                    results.addAll(method.apply(Class.forName(entity.getUserClassQName())));
                }
            }
            return results;
        } catch (Exception e) {

            throw new RuntimeException("Error while running benchmarks", e);
        }
    }

    private static void printResults(Class<?> clazz, Collection<RunResult> runResults) {
        for(RunResult result : runResults) {

            BenchmarkParams params = result.getParams();

            if(params.getMode() == Mode.SingleShotTime && hasBatchSize(clazz)) {
                System.out.println(
                        String.format(
                                "%s/%s, time=%f, avg=%.1f %s, thrput=%.0f op/sec",
                                params.getBenchmark(),
                                printParams(params),
                                result.getPrimaryResult().getScore(),
                                (result.getPrimaryResult().getScore() / getBatchSize(clazz)),
                                result.getPrimaryResult().getScoreUnit(),
                                ((getBatchSize(clazz) / result.getPrimaryResult().getScore()) * 1000 * 1000 * 1000)
                        )
                );
            }

        }
    }

    private static String printParams(BenchmarkParams params) {
        StringBuilder builder = new StringBuilder();

        for(String key : params.getParamsKeys()) {
            String val = params.getParam(key);
            builder.append(key).append("=").append(val).append("/");
        }

        if(builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    private static boolean hasBatchSize(Class<?> clazz) {
        try {

            for(Field field : clazz.getDeclaredFields()) {
                if(field.getName().equals("BATCH_SIZE")) {
                    return true;
                }
            }

            return hasBatchSize(clazz.getSuperclass());
        } catch (Exception e) {

            return false;
        }
    }

    private static int getBatchSize(Class<?> clazz) {
        try {

            for(Field field : clazz.getDeclaredFields()) {
                if(field.getName().equals("BATCH_SIZE")) {
                    field.setAccessible(true);
                    return field.getInt(clazz);
                }
            }

            return getBatchSize(clazz.getSuperclass());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}
