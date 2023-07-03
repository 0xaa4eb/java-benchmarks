package com.chibik.perf.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.openjdk.jmh.annotations.*;

import com.chibik.perf.BenchmarkRunner;

@State(Scope.Benchmark)
public class ToArrayBench {

    private int size = 100;

    @Param({"arraylist", "hashset"})
    String type;

    Collection<Foo> coll;

    @Setup
    public void setup() {
        if (type.equals("arraylist")) {
            coll = new ArrayList<>();
        } else if (type.equals("hashset")) {
            coll = new HashSet<>();
        } else {
            throw new IllegalStateException();
        }
        for (int i = 0; i < size; i++) {
            coll.add(new Foo(i));
        }
    }

    @Benchmark
    public Object[] simple() {
        return coll.toArray();
    }

    @Benchmark
    public Foo[] zero() {
        return coll.toArray(new Foo[0]);
    }

    @Benchmark
    public Foo[] sized() {
        return coll.toArray(new Foo[coll.size()]);
    }

    public static class Foo {
        private int i;

        public Foo(int i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Foo foo = (Foo) o;
            return i == foo.i;
        }

        @Override
        public int hashCode() {
            return i;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(ToArrayBench.class);
    }
}