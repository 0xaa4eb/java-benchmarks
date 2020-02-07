package com.chibik.perf.java8.lambda.util;

public class LImpl2 implements LInterface {

    private final int f2;

    public LImpl2(int f2) {
        this.f2 = f2;
    }

    @Override
    public int foo() {
        return f2;
    }
}
