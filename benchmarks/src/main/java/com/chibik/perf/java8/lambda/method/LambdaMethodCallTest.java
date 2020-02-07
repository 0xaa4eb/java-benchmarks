package com.chibik.perf.java8.lambda.method;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java8.lambda.util.TestEvent;
import com.chibik.perf.java8.lambda.util.TestParam;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*LambdaMethodCallTest.testWithLambda -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class LambdaMethodCallTest {

    private volatile TestEvent e = new TestEvent(3);
    private volatile TestParam z = new TestParam(5);

    private volatile int counter = 0;

    /*
      # {method} {0x00007fe03b56ade0} 'testWithLambda' '()V' in 'com/chibik/perf/java8/lambda/LambdaMethodCallTest'
  #           [sp+0x20]  (sp of caller)
  cmp     rax,qword ptr [rsi+8h]
  jne     7fe149045b60h     ;   {runtime_call}
  nop
  nop     dword ptr [rax+0h]
[Verified Entry Point]
  sub     rsp,18h
  mov     qword ptr [rsp+10h],rbp  ;*synchronization entry
                                                ; - com.chibik.perf.java8.lambda.method.LambdaMethodCallTest$$Lambda$3/1336588754::get$Lambda@-1
                                                ; - java.lang.invoke.LambdaForm$DMH/999966131::invokeStatic_L_L@10
                                                ; - java.lang.invoke.LambdaForm$MH/481604194::linkToTargetMethod@5
                                                ; - com.chibik.perf.java8.lambda.method.LambdaMethodCallTest::testWithLambda@1 (line 24)

  mov     r11d,dword ptr [rsi+10h]
  inc     r11d
  mov     dword ptr [rsi+10h],r11d
  lock add dword ptr [rsp],0h  ;*putfield counter
                                                ; - com.chibik.perf.java8.lambda.method.LambdaMethodCallTest::call@7 (line 29)
                                                ; - com.chibik.perf.java8.lambda.method.LambdaMethodCallTest$$Lambda$3/1336588754::run@4
                                                ; - com.chibik.perf.java8.lambda.method.LambdaMethodCallTest::testWithLambda@8 (line 25)

  add     rsp,10h
  pop     rbp
  test    dword ptr [7fe153da7000h],eax
                            ;   {poll_return}
  ret
    */

    @Benchmark
    public void testWithLambda() {
        Runnable runnable = this::call;
        runnable.run();
    }

    private void call() {
        counter++;
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LambdaMethodCallTest.class);
    }
}
