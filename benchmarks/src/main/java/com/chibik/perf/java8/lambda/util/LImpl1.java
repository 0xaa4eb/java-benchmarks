package com.chibik.perf.java8.lambda.util;

public class LImpl1 implements LInterface {

    private final int f1;

    public LImpl1(int f1) {
        this.f1 = f1;
    }

    @Override
    public int foo() {
        return f1;
    }
}
