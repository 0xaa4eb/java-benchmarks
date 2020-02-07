package com.chibik.perf.java8.lambda;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java8.lambda.util.TestEvent;
import com.chibik.perf.java8.lambda.util.TestParam;
import com.chibik.perf.java8.lambda.util.TestResult;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*SimpleLambdaTest.test2 -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class SimpleLambdaTest {

    private volatile TestEvent e = new TestEvent(3);
    private volatile TestParam z = new TestParam(5);

  /*
  # {method} {0x00007fe0d671a0b8} 'test' '(Lorg/openjdk/jmh/infra/Blackhole;)I' in 'com/chibik/perf/java8/lambda/SimpleLambdaTest'
  # this:     rsi:rsi   = 'com/chibik/perf/java8/lambda/SimpleLambdaTest'
  # parm0:    rdx:rdx   = 'org/openjdk/jmh/infra/Blackhole'
  #           [sp+0x30]  (sp of caller)
  0x00007fe1d8442ce0: cmp     rax,qword ptr [rsi+8h]
  0x00007fe1d8442ce4: jne     7fe1d83f1b60h     ;   {runtime_call}
  0x00007fe1d8442cea: nop
  0x00007fe1d8442cec: nop     dword ptr [rax+0h]
[Verified Entry Point]
  : mov     dword ptr [rsp+0fffffffffffec000h],eax
  : push    rbp
  : sub     rsp,20h           ;*synchronization entry
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@-1 (line 29)
  : mov     r10,qword ptr [rsi+10h]  ;*getfield e
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@8 (line 32)
  : mov     r11,qword ptr [rsi+18h]  ;*getfield z
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@12 (line 32)
  : mov     r8,7fe1839b0498h  ;*synchronization entry
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@-1 (line 29)
                              ;   {oop(a 'com/chibik/perf/java8/lambda/SimpleLambdaTest$$Lambda$3')}
  : mov     r9,qword ptr [r8+8h]
  : mov     rcx,7fe0d67b9028h  ;   {metadata('com/chibik/perf/java8/lambda/SimpleLambdaTest$$Lambda$3')}
  : cmp     r9,rcx
  : jne     7fe1d8442d4dh     ;*invokeinterface apply
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@15 (line 32)
  : test    r10,r10
  : je      7fe1d8442d3bh     ;*invokevirtual getId
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::lambda$test$0@5 (line 30)
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest$$Lambda$3/505180684::apply@8
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@15 (line 32)
  : mov     eax,dword ptr [r11+10h]  ;*synchronization entry
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@-1 (line 29)
                              ; implicit exception: dispatches to 0x00007fe1d8442d69
  : imul    eax,dword ptr [r10+10h]  ;*imul
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::lambda$test$0@12 (line 30)
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest$$Lambda$3/505180684::apply@8
                              ; - com.chibik.perf.java8.lambda.SimpleLambdaTest::test@15 (line 32)
  : add     rsp,20h
  : pop     rbp
  : test    dword ptr [7fe1e2d1a000h],eax
                              ;   {poll_return}
  : ret
    */

    @Benchmark
    public int test(Blackhole bh) {
        BiFunction<TestEvent, TestParam, TestResult> x =
                (u, v) -> new TestResult(u.getId() * v.getId());

        return x.apply(e, z).getId();
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(SimpleLambdaTest.class);
    }
}
