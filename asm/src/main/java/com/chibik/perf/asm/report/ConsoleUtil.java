package com.chibik.perf.asm.report;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.PseudoColumnUsage;

public class ConsoleUtil {

    public static String executeAndCaptureConsoleOutput(Runnable runnable) {
        OutputStream out = new ByteArrayOutputStream();
        PrintStream defaultSystemOut = System.out;
        try {
            System.setOut(new DuplicatingPrintStream(defaultSystemOut, out));
            runnable.run();
            return out.toString();
        } finally {
            System.setOut(defaultSystemOut);
        }
    }

    public static class DuplicatingPrintStream extends PrintStream {

        private final @NotNull OutputStream out2;

        public DuplicatingPrintStream(@NotNull OutputStream primaryOutputStream, @NotNull OutputStream secondaryOutputStream) {
            super(primaryOutputStream);
            this.out2 = secondaryOutputStream;
        }

        @Override
        public void write(int b) {
            super.write(b);
            try {
                this.out2.write(b);
            } catch (IOException e) {
                throw new RuntimeException("Error while writing to secondary stream", e);
            }
        }
    }
}
