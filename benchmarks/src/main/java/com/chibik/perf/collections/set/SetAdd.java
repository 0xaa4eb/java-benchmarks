package com.chibik.perf.collections.set;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.util.Comment;
import com.chibik.perf.util.IncludedInReport;
import com.chibik.perf.util.SingleSnapshotBenchmark;
import gnu.trove.set.hash.TIntHashSet;
import org.openjdk.jmh.annotations.*;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@SingleSnapshotBenchmark(batchSize = SetAdd.BATCH_SIZE)
@IncludedInReport
@Comment("Take array(" + SetAdd.BATCH_SIZE + ") of random values and put them to various sets.")
public class SetAdd {

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
    }

    @Benchmark
    public void hashSet() {
        hashSet.add(index);
    }

    @Benchmark
    public void tIntHashSet() {
        tIntHashSet.add(index);
    }

    @Benchmark
    public void treeSet() {
        treeSet.add(index);
    }

    @TearDown(Level.Invocation)
    public void inc() {
        index++;
    }

    @TearDown(Level.Iteration)
    public void validate() {
        if(hashSet.size() + tIntHashSet.size() + treeSet.size() != BATCH_SIZE) {
            throw new RuntimeException(
                    "Invalid iteration!"
            );
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SetAdd.class);
    }
}
