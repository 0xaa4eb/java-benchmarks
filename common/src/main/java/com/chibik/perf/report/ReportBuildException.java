package com.chibik.perf.report;

public class ReportBuildException extends RuntimeException {
    public ReportBuildException(String message) {
        super(message);
    }

    public ReportBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportBuildException(Throwable cause) {
        super(cause);
    }
}
