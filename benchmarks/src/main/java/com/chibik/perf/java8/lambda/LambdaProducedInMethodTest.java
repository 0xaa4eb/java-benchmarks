package com.chibik.perf.java8.lambda;

import com.chibik.perf.BenchmarkRunner;
import com.chibik.perf.java8.lambda.util.TestEvent;
import com.chibik.perf.java8.lambda.util.TestParam;
import com.chibik.perf.java8.lambda.util.TestResult;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*LambdaProducedInMethodTest.test -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class LambdaProducedInMethodTest {

    private volatile TestEvent e = new TestEvent(3);
    private volatile TestParam z = new TestParam(5);

    @Setup(Level.Iteration)
    public void setUp() {

    }

    /*
      # {method} {0x00007f61b4e65100} 'test' '(Lorg/openjdk/jmh/infra/Blackhole;)I' in 'com/chibik/perf/java8/lambda/LambdaProducedInMethodTest'
  # this:     rsi:rsi   = 'com/chibik/perf/java8/lambda/LambdaProducedInMethodTest'
  # parm0:    rdx:rdx   = 'org/openjdk/jmh/infra/Blackhole'
  #           [sp+0x30]  (sp of caller)
  0x00007f61ad094a00: cmp     rax,qword ptr [rsi+8h]
  0x00007f61ad094a04: jne     7f61ad045b60h     ;   {runtime_call}
  0x00007f61ad094a0a: nop
  0x00007f61ad094a0c: nop     dword ptr [rax+0h]
[Verified Entry Point]
  0x00007f61ad094a10: mov     dword ptr [rsp+0fffffffffffec000h],eax
  0x00007f61ad094a17: push    rbp
  0x00007f61ad094a18: sub     rsp,20h           ;*synchronization entry
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@-1 (line 82)

  0x00007f61ad094a1c: mov     rbp,rsi
  0x00007f61ad094a1f: call    7f61ad045d60h     ; OopMap{rbp=Oop off=36}
                                                ;*invokespecial produce
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@1 (line 82)
                                                ;   {optimized virtual_call}
  0x00007f61ad094a24: mov     r8,qword ptr [rbp+10h]  ;*getfield e
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@5 (line 82)

  0x00007f61ad094a28: mov     rbp,qword ptr [rbp+18h]  ;*getfield z
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@9 (line 82)

  0x00007f61ad094a2c: mov     r10,qword ptr [rax+8h]  ; implicit exception: dispatches to 0x00007f61ad094a8e
  0x00007f61ad094a30: mov     r11,7f61b4f04028h  ;   {metadata('com/chibik/perf/java8/lambda/LambdaProducedInMethodTest$$Lambda$3')}
  0x00007f61ad094a3a: cmp     r10,r11
  0x00007f61ad094a3d: jne     7f61ad094a69h     ;*invokeinterface apply
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@12 (line 82)

  0x00007f61ad094a3f: test    r8,r8
  0x00007f61ad094a42: je      7f61ad094a58h     ;*invokevirtual getId
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::lambda$produce$0@5 (line 87)
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest$$Lambda$3/1995995151::apply@8
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@12 (line 82)

  0x00007f61ad094a44: mov     eax,dword ptr [rbp+10h]  ;*invokespecial produce
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@1 (line 82)
                                                ; implicit exception: dispatches to 0x00007f61ad094aa1
  0x00007f61ad094a47: imul    eax,dword ptr [r8+10h]  ;*imul
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::lambda$produce$0@12 (line 87)
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest$$Lambda$3/1995995151::apply@8
                                                ; - com.chibik.perf.java8.lambda.LambdaProducedInMethodTest::test@12 (line 82)

  0x00007f61ad094a4c: add     rsp,20h
  0x00007f61ad094a50: pop     rbp
  0x00007f61ad094a51: test    dword ptr [7f61ba34d000h],eax
                                                ;   {poll_return}
  0x00007f61ad094a57: ret
    */

    @Benchmark
    public int test(Blackhole bh) {

        return produce().apply(e, z).getId();
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private BiFunction<TestEvent, TestParam, TestResult> produce() {
        return (u, v) -> new TestResult(u.getId() * v.getId());
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(LambdaProducedInMethodTest.class);
    }
}
