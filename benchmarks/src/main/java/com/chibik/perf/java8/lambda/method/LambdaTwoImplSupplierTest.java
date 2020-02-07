package com.chibik.perf.java8.lambda.method;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java8.lambda.util.LImpl1;
import com.chibik.perf.java8.lambda.util.LInterface;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*LambdaTwoImplSupplierTest.testWithLambda -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class LambdaTwoImplSupplierTest {

    private volatile int counter = 0;
    private volatile IntSupplier o;

    private volatile LInterface[] i = new LInterface[]{new LImpl1(154), new LImpl1(10)};

    private int z = 0;

    @Benchmark
    public void testWithLambda() {
        z = 1 - z;

        LInterface lInt = i[z];
        doLogic(lInt::foo);
    }

    //allocate or do not allocate garbage depending on absence of inlining
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private void doLogic(IntSupplier s) {
        counter = s.getAsInt();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LambdaTwoImplSupplierTest.class);
    }
}
