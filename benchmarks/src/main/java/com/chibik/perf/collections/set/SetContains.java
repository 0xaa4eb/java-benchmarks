package com.chibik.perf.collections.set;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import gnu.trove.set.hash.TIntHashSet;
import org.openjdk.jmh.annotations.*;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(batchSize = SetContains.BATCH_SIZE, iterations = 20, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(batchSize = SetContains.BATCH_SIZE, iterations = 30, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@IncludedInReport
@Comment("Take array(" + SetAdd.BATCH_SIZE + ") of random values and put them to various sets. Benchmark measures contains() method time")
public class SetContains {

    @Param({"16", "" + BATCH_SIZE})
    private int preallocatedSize;

    public static final int BATCH_SIZE = 1000000;

    private HashSet<Integer> hashSet;
    private TreeSet<Integer> treeSet;
    private TIntHashSet tIntHashSet;

    private int index;

    @Setup(Level.Iteration)
    public void setUp() {
        index = 0;
        hashSet = new HashSet<>(preallocatedSize);
        treeSet = new TreeSet<>();
        tIntHashSet = new TIntHashSet(preallocatedSize);
        for(int i = 0; i < BATCH_SIZE; i++) {
            hashSet.add(i);
            treeSet.add(i);
            tIntHashSet.add(i);
        }
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    @TearDown(Level.Iteration)
    public void validate() {
        if(hashSet.size() + tIntHashSet.size() + treeSet.size() != 3 * BATCH_SIZE) {
            throw new RuntimeException("Invalid iteration!");
        }
    }

    @Benchmark
    public boolean hashSet() {
        return hashSet.contains(index);
    }

    @Benchmark
    public boolean tIntHashSet() {
        return tIntHashSet.contains(index);
    }

    @Benchmark
    public boolean treeSet() {
        return treeSet.contains(index);
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SetContains.class);
    }
}
