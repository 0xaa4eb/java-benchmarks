package com.chibik.perf.java.inlining.storage;

public class PojoStorage implements Storage {

    private String x;
    private String y;
    private String z;

    @Override
    public String getX() {
        return x;
    }

    @Override
    public void setX(String x) {
        this.x = x;
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

class PojoStorage2 extends PojoStorage {

    @Override
    public String getX() {
        return super.getX();
    }
}