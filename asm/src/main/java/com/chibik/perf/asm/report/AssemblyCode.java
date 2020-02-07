package com.chibik.perf.asm.report;

import java.util.Objects;

public class AssemblyCode {

    private final MethodName method;
    private final String code;

    public AssemblyCode(String className, String methodName, String code) {
        this.method = new MethodName(className, methodName);
        this.code = code;
    }

    public String getClassName() {
        return method.getClassName();
    }

    public String getMethodName() {
        return method.getName();
    }

    public MethodName getMethod() {
        return method;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssemblyCode that = (AssemblyCode) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, code);
    }

    @Override
    public String toString() {
        return "AssemblyCode{" +
                "method=" + method +
                ", code='" + code + '\'' +
                '}';
    }
}
