package com.chibik.perf.asm.report;

import com.chibik.perf.report.ReportBuildException;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import static org.junit.Assert.*;

public class SourceFileReaderTest {

    @Test(expected = ReportBuildException.class)
    public void testUnknown() {
        var dao = new SourceFileReader();
        dao.get("com.chibik.perf.sdsad");
    }

    @Test
    public void testGetSourceForTestClass() {
        var dao = new SourceFileReader();
        ClassFileSource classFileSource = dao.get("com.chibik.perf.asm.report.MethodName");
        assertThat(classFileSource.getSourceText(), JUnitMatchers.containsString("public class MethodName"));
    }
}