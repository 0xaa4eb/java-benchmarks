package com.chibik.perf;

import com.chibik.perf.report.*;
import com.chibik.perf.util.*;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.RunResult;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.chibik.perf.report.HTMLElement.h3;
import static com.chibik.perf.report.HTMLElement.text;
import static java.util.stream.Collectors.toList;

public class BenchmarkReportGenerator {

    private final Collection<RunResult> runResults;

    public BenchmarkReportGenerator(Collection<RunResult> runResults) {
        this.runResults = new ArrayList<>(runResults);
    }

    public void build(OutputStream target) {
        HTMLPageBuilder page = new HTMLPageBuilder("Benchmarks results");

        List<Map.Entry<String, List<RunResult>>> sortedResults =
                runResults.stream().collect(
                        Collectors.groupingBy(
                                x -> x.getParams().getBenchmark().substring(0, x.getParams().getBenchmark().lastIndexOf(".")),
                                toList()
                        ))
                        .entrySet()
                        .stream()
                        .sorted(Comparator.comparing(Map.Entry<String, List<RunResult>>::getKey))
                        .collect(toList());

        for (Map.Entry<String, List<RunResult>> entry : sortedResults) {
            Class<?> benchmarkClass;
            try {
                benchmarkClass = this.getClass().getClassLoader().loadClass(entry.getKey());
            } catch (ClassNotFoundException e) {
                throw new ReportBuildException("Class not found", e);
            }

            page.append(h3(text(benchmarkClass.getName())));

            RunResult first = entry.getValue().get(0);
            List<String> parameterKeys = new ArrayList<>(first.getParams().getParamsKeys());
            boolean batchMeasurement = first.getParams().getMeasurement().getBatchSize() > 1;
            List<HTMLElement> columns = new ArrayList<>();
            columns.add(text("Benchmark"));
            columns.addAll(parameterKeys.stream().map(HTMLText::of).collect(Collectors.toList()));
            if (batchMeasurement) {
                columns.add(text("Batch size"));
                columns.add(text("Score(total)"));
                columns.add(text("Score unit"));
                columns.add(text("Per op(per call)"));
                columns.add(text("Per op unit"));
                columns.add(text("Allocated"));
                columns.add(text("Allocated unit"));
            } else {
                columns.add(text("Score"));
                columns.add(text("Score unit"));
                columns.add(text("Allocated"));
                columns.add(text("Allocated unit"));
            }
            columns.add(HTMLElement.text("Description"));

            Comment comment = benchmarkClass.getAnnotation(Comment.class);
            if (comment != null) {
                page.append(HTMLText.of(comment.value()));
            }

            HTMLTable table = new HTMLTable(new HTMLTable.Props(), columns);

            for (RunResult result : entry.getValue()) {

                try {
                    String label = result.getPrimaryResult().getLabel();
                    Method benchmarkMethod = null;
                    for (Method method : benchmarkClass.getDeclaredMethods()) {
                        if (method.getName().equals(label)) {
                            benchmarkMethod = method;
                            break;
                        }
                    }

                    if (benchmarkMethod == null) {
                        throw new ReportBuildException("Could not find method for run result " + result);
                    }

                    final Mode mode = result.getParams().getMode();
                    final String benchmark = result.getParams().getBenchmark();
                    final String shortBenchmark = benchmark.substring(benchmark.lastIndexOf(".") + 1);

                    List<HTMLElement> row = new ArrayList<>();
                    row.add(HTMLText.of(shortBenchmark));

                    for (String paramKey : parameterKeys) {
                        row.add(HTMLText.of(result.getParams().getParam(paramKey)));
                    }

                    if (batchMeasurement) {
                        int batchSize = result.getParams().getMeasurement().getBatchSize();
                        row.add(text(batchSize));
                    }

                    Score score = new Score(
                            result.getPrimaryResult().getScore(),
                            ScoreTimeUnit.byTimeUnit(result.getPrimaryResult().getScoreUnit())
                    );

                    row.add(text(String.format("%.3f", score.getVal())));
                    row.add(text(score.getUnit().getValue(mode)));

                    if (batchMeasurement) {
                        Score batchPerOpScore;
                        Score convertedPerOpScore;
                        int batchSize = result.getParams().getMeasurement().getBatchSize();
                        if (mode == Mode.Throughput) {
                            batchPerOpScore = new Score(score.getVal() * batchSize, score.getUnit());
                        } else {
                            batchPerOpScore = new Score(score.getVal() / batchSize, score.getUnit());
                        }
                        convertedPerOpScore = ScoreTimeUnit.convertToReadable(batchPerOpScore);

                        row.add(text(String.format("%.3f", convertedPerOpScore.getVal())));
                        row.add(text(convertedPerOpScore.getUnit().getValue(mode)));
                    }
                    row.add(text(String.format("%.3f", result.getSecondaryResults().get("·gc.alloc.rate.norm").getScore())));
                    row.add(text(result.getSecondaryResults().get("·gc.alloc.rate.norm").getScoreUnit()));

                    Comment methodComment = benchmarkMethod.getAnnotation(Comment.class);
                    if (methodComment != null) {
                        row.add(text(methodComment.value()));
                    } else {
                        row.add(text("No comment"));
                    }

                    table.addRowValues(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            page.append(table);
        }

        try {
            target.write(page.render().getBytes());
        } catch (IOException e) {
            throw new ReportBuildException("Error while building report", e);
        }
    }
}
