package com.chibik.perf.java.inlining;

import com.chibik.perf.BenchmarkRunner;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*CallOverridenSingleImpl.overriddenMethod -XX:-TieredCompilation -XX:BiasedLockingStartupDelay=0 -server -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class CallOverridenSingleImpl {

    private ParentClass obj = new ChildClass();

    @Setup(Level.Iteration)
    public void setUp() {
        obj.setX(ThreadLocalRandom.current().nextInt(1024 * 1024));
    }

    /*
    [Verified Entry Point]
  mov    DWORD PTR [rsp-0x14000],eax
  push   rbp
  sub    rsp,0x10           ;*synchronization entry
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@-1 (line 29)

  mov    r10,QWORD PTR [rsi+0x10]  ;*getfield obj
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@1 (line 29)

  mov    eax,DWORD PTR [r10+0x10]  ; implicit exception: dispatches to 0x0000000107e95712
  inc    eax                ;*iadd
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl$ParentClass::finalMethod@5 (line 50)
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@4 (line 29)

  add    rsp,0x10
  pop    rbp
  test   DWORD PTR [rip+0xffffffffff5b38ef],eax        # 0x0000000107449000
                                                ;   {poll_return}
  ret

    * */

    @Benchmark
    public int finalMethod() {
        return obj.finalMethod();
    }

    /*
  mov    DWORD PTR [rsp-0x14000],eax
  push   rbp
  sub    rsp,0x10           ;*synchronization entry
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@-1 (line 52)

  mov    r10,QWORD PTR [rsi+0x10]  ;*getfield obj
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@1 (line 52)

  mov    eax,DWORD PTR [r10+0x10]  ; implicit exception: dispatches to 0x000000010b28e412
  inc    eax                ;*iadd
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl$ParentClass::finalMethod@5 (line 73)
                                                ; - com.chibik.perf.java.inlining.CallOverridenSingleImpl::finalMethod@4 (line 52)

  add    rsp,0x10
  pop    rbp
  test   DWORD PTR [rip+0xffffffffff5e6bef],eax        # 0x000000010a875000
                                                ;   {poll_return}
  ret

    * */

    @Benchmark
    public int overriddenMethod() {
        return obj.overriddenMethod();
    }

    public static class ParentClass {

        protected int x;

        public void setX(int x) {
            this.x = x;
        }

        public int overriddenMethod() {
            return x + 1;
        }

        public int finalMethod() {
            return x + 1;
        }
    }

    public static class ChildClass extends ParentClass {

        @Override
        public int overriddenMethod() {
            return x - 1;
        }
    }

    public static void main(String[] args) {
        BenchmarkRunner.run(CallOverridenSingleImpl.class);
    }

}
