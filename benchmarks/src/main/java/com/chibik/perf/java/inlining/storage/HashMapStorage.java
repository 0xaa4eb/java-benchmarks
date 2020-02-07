package com.chibik.perf.java.inlining.storage;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorage implements Storage {

    private String x;
    private String y;
    private String z;

    @Override
    public String getX() {
        return x;
    }

    @Override
    public void setX(String x) {
        this.x = x + "a";
    }

    @Override
    public String getY() {
        return y;
    }

    @Override
    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String getZ() {
        return z;
    }

    @Override
    public void setZ(String z) {
        this.z = z;
    }
}
