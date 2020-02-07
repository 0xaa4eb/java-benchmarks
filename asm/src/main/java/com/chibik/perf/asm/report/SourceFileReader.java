package com.chibik.perf.asm.report;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class SourceFileReader {

    ClassFileSource get(String className) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(className.replace('.', '/') + ".java");
        if (resource == null) {
            throw new ReportBuildException("Could not find source file of class " + className);
        }
        try (InputStream inputStream = resource.openStream()) {
            String text = new String(inputStream.readAllBytes(), Charset.defaultCharset());
            text = text.replace("\r", "");
            return new ClassFileSource(className, text);
        } catch (IOException e) {
            throw new RuntimeException("Error reading class " + className, e);
        }
    }
}
