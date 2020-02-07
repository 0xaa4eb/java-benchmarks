package com.chibik.perf.asm.report;

import java.util.Objects;

public class MethodName {
    private final String className;
    private final String name;

    public MethodName(String className, String name) {
        this.className = className;
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodName that = (MethodName) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, name);
    }
}
