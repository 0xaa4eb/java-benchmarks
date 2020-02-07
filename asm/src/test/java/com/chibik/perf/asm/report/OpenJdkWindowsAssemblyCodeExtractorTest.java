package com.chibik.perf.asm.report;

import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class OpenJdkWindowsAssemblyCodeExtractorTest {

    @Test
    public void emptyOutput_shouldFindNoMethods() {
        assertTrue(new OpenJdkWindowsAssemblyCodeExtractor(":asdasdgsadasdasd").extractC1Compiled().isEmpty());
        assertTrue(new OpenJdkWindowsAssemblyCodeExtractor(":asdasdgsadasdasd").extractC2Compiled().isEmpty());
    }

    @Test
    public void testSimpleBenchmarkWith2Methods() {
        String exampleSysOutOfBenchmark = "C:\\Programs\\jdk-13.0.2\\bin\\java.exe \"-javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\lib\\idea_rt.jar=61274:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\bin\" -Dfile.encoding=UTF-8 -classpath C:\\Work\\test-bench\\asm\\target\\classes;C:\\Work\\test-bench\\common\\target\\classes;C:\\Users\\aache\\.m2\\repository\\org\\openjdk\\jmh\\jmh-core\\1.12\\jmh-core-1.12.jar;C:\\Users\\aache\\.m2\\repository\\net\\sf\\jopt-simple\\jopt-simple\\4.6\\jopt-simple-4.6.jar;C:\\Users\\aache\\.m2\\repository\\org\\apache\\commons\\commons-math3\\3.2\\commons-math3-3.2.jar;C:\\Users\\aache\\.m2\\repository\\org\\javassist\\javassist\\3.20.0-GA\\javassist-3.20.0-GA.jar;C:\\Users\\aache\\.m2\\repository\\net\\sf\\trove4j\\trove4j\\3.0.3\\trove4j-3.0.3.jar;C:\\Users\\aache\\.m2\\repository\\com\\goldmansachs\\gs-collections\\4.1.0\\gs-collections-4.1.0.jar;C:\\Users\\aache\\.m2\\repository\\com\\goldmansachs\\gs-collections-api\\4.1.0\\gs-collections-api-4.1.0.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-engine\\1.12.11\\chronicle-engine-1.12.11.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-wire\\1.6.2\\chronicle-wire-1.6.2.jar;C:\\Users\\aache\\.m2\\repository\\com\\intellij\\annotations\\12.0\\annotations-12.0.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-queue\\4.4.3\\chronicle-queue-4.4.3.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-map\\2.4.15\\chronicle-map-2.4.15.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\compiler\\2.2.4\\compiler-2.2.4.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\lang\\6.8.1\\lang-6.8.1.jar;C:\\Users\\aache\\.m2\\repository\\org\\ow2\\asm\\asm\\5.0.4\\asm-5.0.4.jar;C:\\Users\\aache\\.m2\\repository\\org\\xerial\\snappy\\snappy-java\\1.1.2.1\\snappy-java-1.1.2.1.jar;C:\\Users\\aache\\.m2\\repository\\com\\thoughtworks\\xstream\\xstream\\1.4.8\\xstream-1.4.8.jar;C:\\Users\\aache\\.m2\\repository\\xmlpull\\xmlpull\\1.1.3.1\\xmlpull-1.1.3.1.jar;C:\\Users\\aache\\.m2\\repository\\xpp3\\xpp3_min\\1.1.4c\\xpp3_min-1.1.4c.jar;C:\\Users\\aache\\.m2\\repository\\org\\codehaus\\jettison\\jettison\\1.3.7\\jettison-1.3.7.jar;C:\\Users\\aache\\.m2\\repository\\stax\\stax-api\\1.0.1\\stax-api-1.0.1.jar;C:\\Users\\aache\\.m2\\repository\\org\\ops4j\\pax\\url\\pax-url-aether\\2.4.5\\pax-url-aether-2.4.5.jar;C:\\Users\\aache\\.m2\\repository\\org\\slf4j\\jcl-over-slf4j\\1.6.6\\jcl-over-slf4j-1.6.6.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-network\\1.6.5\\chronicle-network-1.6.5.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-core\\1.6.1\\chronicle-core-1.6.1.jar;C:\\Users\\aache\\.m2\\repository\\org\\slf4j\\slf4j-api\\1.7.14\\slf4j-api-1.7.14.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-bytes\\1.6.1\\chronicle-bytes-1.6.1.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\chronicle-threads\\1.6.1\\chronicle-threads-1.6.1.jar;C:\\Users\\aache\\.m2\\repository\\net\\openhft\\affinity\\3.0.5\\affinity-3.0.5.jar;C:\\Users\\aache\\.m2\\repository\\net\\java\\dev\\jna\\jna\\4.2.1\\jna-4.2.1.jar;C:\\Users\\aache\\.m2\\repository\\net\\java\\dev\\jna\\jna-platform\\4.2.1\\jna-platform-4.2.1.jar;C:\\Users\\aache\\.m2\\repository\\org\\jctools\\jctools-core\\1.2.1\\jctools-core-1.2.1.jar;C:\\Users\\aache\\.m2\\repository\\com\\itextpdf\\itextpdf\\5.5.10\\itextpdf-5.5.10.jar com.chibik.perf.asm.constfold.StringConstandFolding\n" +
                "WARNING: An illegal reflective access operation has occurred\n" +
                "WARNING: Illegal reflective access by org.openjdk.jmh.util.Utils (file:/C:/Users/aache/.m2/repository/org/openjdk/jmh/jmh-core/1.12/jmh-core-1.12.jar) to field java.io.PrintStream.charOut\n" +
                "WARNING: Please consider reporting this to the maintainers of org.openjdk.jmh.util.Utils\n" +
                "WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations\n" +
                "WARNING: All illegal access operations will be denied in a future release\n" +
                "# JMH 1.12 (released 1405 days ago, please consider updating!)\n" +
                "# VM version: JDK 13.0.2, VM 13.0.2+8\n" +
                "# VM invoker: C:\\Programs\\jdk-13.0.2\\bin\\java.exe\n" +
                "# VM options: -javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\lib\\idea_rt.jar=61274:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\bin -Dfile.encoding=UTF-8 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*StringConstandFolding.* -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops -Xmx8G -ea\n" +
                "# Warmup: 5 iterations, 1 s each\n" +
                "# Measurement: 5 iterations, 1 s each\n" +
                "# Timeout: 10 min per iteration\n" +
                "# Threads: 1 thread, will synchronize iterations\n" +
                "# Benchmark mode: Average time, time/op\n" +
                "# Benchmark: com.chibik.perf.asm.constfold.StringConstandFolding.returnStringItself\n" +
                "\n" +
                "# Run progress: 0,00% complete, ETA 00:00:20\n" +
                "# Fork: 1 of 1\n" +
                "OCompileCommand: print *StringConstandFolding.*\n" +
                "penJDK 64-Bit Server VM warning: printing of assembly code is enabled; turning on DebugNonSafepoints to gain additional output\n" +
                "# Warmup Iteration   1: \n" +
                "============================= C1-compiled nmethod ==============================\n" +
                "----------------------------------- Assembly -----------------------------------\n" +
                "PrintAssemblyOptions help:\n" +
                "  print-raw       test plugin by requesting raw output\n" +
                "  print-raw-xml   test plugin by requesting raw xml\n" +
                "\n" +
                "  show-pc            toggle printing current pc,        currently ON\n" +
                "  show-offset        toggle printing current offset,    currently OFF\n" +
                "  show-bytes         toggle printing instruction bytes, currently OFF\n" +
                "  show-data-hex      toggle formatting data as hex,     currently ON\n" +
                "  show-data-int      toggle formatting data as int,     currently OFF\n" +
                "  show-data-float    toggle formatting data as float,   currently OFF\n" +
                "  show-structs       toggle compiler data structures,   currently ON\n" +
                "  show-comment       toggle instruction comments,       currently ON\n" +
                "  show-block-comment toggle block comments,             currently ON\n" +
                "  align-instr        toggle instruction alignment,      currently ON\n" +
                "combined options: intel,hsdis-help\n" +
                "\n" +
                "Compiled method (c1)     518  698       1       com.chibik.perf.asm.constfold.StringConstandFolding::returnStringItself (3 bytes)\n" +
                " total in heap  [0x0000021496562f10,0x0000021496563208] = 760\n" +
                " relocation     [0x0000021496563078,0x00000214965630a0] = 40\n" +
                " main code      [0x00000214965630a0,0x0000021496563120] = 128\n" +
                " stub code      [0x0000021496563120,0x00000214965631b8] = 152\n" +
                " oops           [0x00000214965631b8,0x00000214965631c0] = 8\n" +
                " metadata       [0x00000214965631c0,0x00000214965631c8] = 8\n" +
                " scopes data    [0x00000214965631c8,0x00000214965631d0] = 8\n" +
                " scopes pcs     [0x00000214965631d0,0x0000021496563200] = 48\n" +
                " dependencies   [0x0000021496563200,0x0000021496563208] = 8\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "[Constant Pool (empty)]\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "\n" +
                "Argument 0 is unknown.RIP: 0x214965630a0 Code size: 0x00000118\n" +
                "[Entry Point]\n" +
                "  # {method} {0x00000216b549fd28} 'returnStringItself' '()Ljava/lang/String;' in 'com/chibik/perf/asm/constfold/StringConstandFolding'\n" +
                "  #           [sp+0x40]  (sp of caller)\n" +
                "  0x00000214965630a0:   nop     word ptr [rax+rax+0h]\n" +
                "  0x00000214965630ab:   nop\n" +
                "  0x00000214965630af:   nop     dword ptr [rax+0h]\n" +
                "  0x00000214965630b6:   cmp     rax,qword ptr [rdx+8h]\n" +
                "  0x00000214965630ba:   jne     2148ea9b000h                ;   {runtime_call ic_miss_stub}\n" +
                "[Verified Entry Point]\n" +
                "  0x00000214965630c0:   mov     dword ptr [rsp+0ffffffffffff9000h],eax\n" +
                "  0x00000214965630c7:   push    rbp\n" +
                "  0x00000214965630c8:   sub     rsp,30h                     ;*ldc {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringItself@0 (line 24)\n" +
                "  0x00000214965630cc:   mov     rax,214ad5cc0f8h            ;   {oop(\"asdfgdshjffasd\"{0x00000214ad5cc0f8})}\n" +
                "  0x00000214965630d6:   add     rsp,30h\n" +
                "  0x00000214965630da:   pop     rbp\n" +
                "  0x00000214965630db:   mov     r10,qword ptr [r15+108h]\n" +
                "  0x00000214965630e2:   test    dword ptr [r10],eax         ;   {poll_return}\n" +
                "  0x00000214965630e5:   ret\n" +
                "  0x00000214965630e6:   nop\n" +
                "  0x00000214965630e7:   nop\n" +
                "  0x00000214965630e8:   mov     rax,qword ptr [r15+3f0h]\n" +
                "  0x00000214965630ef:   mov     r10,0h\n" +
                "  0x00000214965630f9:   mov     qword ptr [r15+3f0h],r10\n" +
                "  0x0000021496563100:   mov     r10,0h\n" +
                "  0x000002149656310a:   mov     qword ptr [r15+3f8h],r10\n" +
                "  0x0000021496563111:   add     rsp,30h\n" +
                "  0x0000021496563115:   pop     rbp\n" +
                "  0x0000021496563116:   jmp     2148eab7d00h                ;   {runtime_call unwind_exception Runtime1 stub}\n" +
                "  0x000002149656311b:   hlt\n" +
                "  0x000002149656311c:   hlt\n" +
                "  0x000002149656311d:   hlt\n" +
                "  0x000002149656311e:   hlt\n" +
                "  0x000002149656311f:   hlt\n" +
                "[Exception Handler]\n" +
                "  0x0000021496563120:   call    2148eb47a80h                ;   {no_reloc}\n" +
                "  0x0000021496563125:   mov     qword ptr [rsp+0ffffffffffffffd8h],rsp\n" +
                "  0x000002149656312a:   sub     rsp,80h\n" +
                "  0x0000021496563131:   mov     qword ptr [rsp+78h],rax\n" +
                "  0x0000021496563136:   mov     qword ptr [rsp+70h],rcx\n" +
                "  0x000002149656313b:   mov     qword ptr [rsp+68h],rdx\n" +
                "  0x0000021496563140:   mov     qword ptr [rsp+60h],rbx\n" +
                "  0x0000021496563145:   mov     qword ptr [rsp+50h],rbp\n" +
                "  0x000002149656314a:   mov     qword ptr [rsp+48h],rsi\n" +
                "  0x000002149656314f:   mov     qword ptr [rsp+40h],rdi\n" +
                "  0x0000021496563154:   mov     qword ptr [rsp+38h],r8\n" +
                "  0x0000021496563159:   mov     qword ptr [rsp+30h],r9\n" +
                "  0x000002149656315e:   mov     qword ptr [rsp+28h],r10\n" +
                "  0x0000021496563163:   mov     qword ptr [rsp+20h],r11\n" +
                "  0x0000021496563168:   mov     qword ptr [rsp+18h],r12\n" +
                "  0x000002149656316d:   mov     qword ptr [rsp+10h],r13\n" +
                "  0x0000021496563172:   mov     qword ptr [rsp+8h],r14\n" +
                "  0x0000021496563177:   mov     qword ptr [rsp],r15\n" +
                "  0x000002149656317b:   mov     rcx,7ffd933cc598h           ;   {external_word}\n" +
                "  0x0000021496563185:   mov     rdx,21496563125h            ;   {internal_word}\n" +
                "  0x000002149656318f:   mov     r8,rsp\n" +
                "  0x0000021496563192:   and     rsp,0fffffffffffffff0h\n" +
                "  0x0000021496563196:   mov     r10,7ffd930ed140h           ;   {runtime_call}\n" +
                "  0x00000214965631a0:   call indirect r10\n" +
                "  0x00000214965631a3:   hlt\n" +
                "[Deopt Handler Code]\n" +
                "  0x00000214965631a4:   mov     r10,214965631a4h            ;   {section_word}\n" +
                "  0x00000214965631ae:   push    r10\n" +
                "  0x00000214965631b0:   jmp     2148ea993a0h                ;   {runtime_call DeoptimizationBlob}\n" +
                "  0x00000214965631b5:   hlt\n" +
                "  0x00000214965631b6:   hlt\n" +
                "  0x00000214965631b7:   hlt\n" +
                "--------------------------------------------------------------------------------\n" +
                "4,116 ns/op\n" +
                "# Warmup Iteration   2: 3,801 ns/op\n" +
                "# Warmup Iteration   3: 3,333 ns/op\n" +
                "# Warmup Iteration   4: 3,247 ns/op\n" +
                "# Warmup Iteration   5: 3,099 ns/op\n" +
                "Iteration   1: 3,180 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   2: 3,286 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   3: 3,468 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   4: 3,438 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   5: 3,685 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "\n" +
                "\n" +
                "Result \"returnStringItself\":\n" +
                "  3,411 ±(99.9%) 0,742 ns/op [Average]\n" +
                "  (min, avg, max) = (3,180, 3,411, 3,685), stdev = 0,193\n" +
                "  CI (99.9%): [2,670, 4,153] (assumes normal distribution)\n" +
                "\n" +
                "Secondary result \"·gc.alloc.rate\":\n" +
                "  0,001 ±(99.9%) 0,001 MB/sec [Average]\n" +
                "  (min, avg, max) = (0,001, 0,001, 0,001), stdev = 0,001\n" +
                "  CI (99.9%): [0,001, 0,001] (assumes normal distribution)\n" +
                "\n" +
                "Secondary result \"·gc.alloc.rate.norm\":\n" +
                "  ≈ 10⁻⁶ B/op\n" +
                "\n" +
                "Secondary result \"·gc.count\":\n" +
                "  ≈ 0 counts\n" +
                "\n" +
                "\n" +
                "# JMH 1.12 (released 1405 days ago, please consider updating!)\n" +
                "# VM version: JDK 13.0.2, VM 13.0.2+8\n" +
                "# VM invoker: C:\\Programs\\jdk-13.0.2\\bin\\java.exe\n" +
                "# VM options: -javaagent:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\lib\\idea_rt.jar=61274:C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2019.3.1\\bin -Dfile.encoding=UTF-8 -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*StringConstandFolding.* -XX:PrintAssemblyOptions=intel,hsdis-help -XX:-UseCompressedOops -Xmx8G -ea\n" +
                "# Warmup: 5 iterations, 1 s each\n" +
                "# Measurement: 5 iterations, 1 s each\n" +
                "# Timeout: 10 min per iteration\n" +
                "# Threads: 1 thread, will synchronize iterations\n" +
                "# Benchmark mode: Average time, time/op\n" +
                "# Benchmark: com.chibik.perf.asm.constfold.StringConstandFolding.returnStringLen\n" +
                "\n" +
                "# Run progress: 50,00% complete, ETA 00:00:10\n" +
                "# Fork: 1 of 1\n" +
                "CompileCommand: print *StringConstandFolding.*\n" +
                "OpenJDK 64-Bit Server VM warning: printing of assembly code is enabled; turning on DebugNonSafepoints to gain additional output\n" +
                "# Warmup Iteration   1: \n" +
                "============================= C1-compiled nmethod ==============================\n" +
                "----------------------------------- Assembly -----------------------------------\n" +
                "PrintAssemblyOptions help:\n" +
                "  print-raw       test plugin by requesting raw output\n" +
                "  print-raw-xml   test plugin by requesting raw xml\n" +
                "\n" +
                "  show-pc            toggle printing current pc,        currently ON\n" +
                "  show-offset        toggle printing current offset,    currently OFF\n" +
                "  show-bytes         toggle printing instruction bytes, currently OFF\n" +
                "  show-data-hex      toggle formatting data as hex,     currently ON\n" +
                "  show-data-int      toggle formatting data as int,     currently OFF\n" +
                "  show-data-float    toggle formatting data as float,   currently OFF\n" +
                "  show-structs       toggle compiler data structures,   currently ON\n" +
                "  show-comment       toggle instruction comments,       currently ON\n" +
                "  show-block-comment toggle block comments,             currently ON\n" +
                "  align-instr        toggle instruction alignment,      currently ON\n" +
                "combined options: intel,hsdis-help\n" +
                "\n" +
                "Compiled method (c1)     438  686       3       com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen (6 bytes)\n" +
                " total in heap  [0x000001853c44c310,0x000001853c44c818] = 1288\n" +
                " relocation     [0x000001853c44c478,0x000001853c44c4b8] = 64\n" +
                " main code      [0x000001853c44c4c0,0x000001853c44c680] = 448\n" +
                " stub code      [0x000001853c44c680,0x000001853c44c718] = 152\n" +
                " oops           [0x000001853c44c718,0x000001853c44c728] = 16\n" +
                " metadata       [0x000001853c44c728,0x000001853c44c740] = 24\n" +
                " scopes data    [0x000001853c44c740,0x000001853c44c790] = 80\n" +
                " scopes pcs     [0x000001853c44c790,0x000001853c44c810] = 128\n" +
                " dependencies   [0x000001853c44c810,0x000001853c44c818] = 8\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "[Constant Pool (empty)]\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "\n" +
                "Argument 0 is unknown.RIP: 0x1853c44c4c0 Code size: 0x00000258\n" +
                "[Entry Point]\n" +
                "  # {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding'\n" +
                "  #           [sp+0x50]  (sp of caller)\n" +
                "  0x000001853c44c4c0:   nop     word ptr [rax+rax+0h]\n" +
                "  0x000001853c44c4cb:   nop\n" +
                "  0x000001853c44c4cf:   nop     dword ptr [rax+0h]\n" +
                "  0x000001853c44c4d6:   cmp     rax,qword ptr [rdx+8h]\n" +
                "  0x000001853c44c4da:   jne     1853bdbb000h                ;   {runtime_call ic_miss_stub}\n" +
                "[Verified Entry Point]\n" +
                "  0x000001853c44c4e0:   mov     dword ptr [rsp+0ffffffffffff9000h],eax\n" +
                "  0x000001853c44c4e7:   push    rbp\n" +
                "  0x000001853c44c4e8:   sub     rsp,40h\n" +
                "  0x000001853c44c4ec:   mov     rax,187627c5dc0h            ;   {metadata(method data for {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c4f6:   mov     esi,dword ptr [rax+104h]\n" +
                "  0x000001853c44c4fc:   add     esi,8h\n" +
                "  0x000001853c44c4ff:   mov     dword ptr [rax+104h],esi\n" +
                "  0x000001853c44c505:   and     esi,1ff8h\n" +
                "  0x000001853c44c50b:   cmp     esi,0h\n" +
                "  0x000001853c44c50e:   je      1853c44c5cah                ;*ldc {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@0 (line 19)\n" +
                "  0x000001853c44c514:   mov     rax,1855a7c5f28h            ;   {oop(\"asdfgdshjffasd\"{0x000001855a7c5f28})}\n" +
                "  0x000001853c44c51e:   mov     rsi,rax\n" +
                "  0x000001853c44c521:   mov     rdi,187627c5dc0h            ;   {metadata(method data for {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c52b:   add     qword ptr [rdi+148h],1h\n" +
                "  0x000001853c44c533:   mov     rsi,1876106c398h            ;   {metadata(method data for {method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c53d:   mov     edi,dword ptr [rsi+104h]\n" +
                "  0x000001853c44c543:   add     edi,8h\n" +
                "  0x000001853c44c546:   mov     dword ptr [rsi+104h],edi\n" +
                "  0x000001853c44c54c:   and     edi,7ffff8h\n" +
                "  0x000001853c44c552:   cmp     edi,0h\n" +
                "  0x000001853c44c555:   je      1853c44c5ebh\n" +
                "  0x000001853c44c55b:   mov     rsi,1876106c398h            ;   {metadata(method data for {method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c565:   add     qword ptr [rsi+148h],1h\n" +
                "  0x000001853c44c56d:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c577:   mov     esi,dword ptr [rax+104h]\n" +
                "  0x000001853c44c57d:   add     esi,8h\n" +
                "  0x000001853c44c580:   mov     dword ptr [rax+104h],esi\n" +
                "  0x000001853c44c586:   and     esi,7ffff8h\n" +
                "  0x000001853c44c58c:   cmp     esi,0h\n" +
                "  0x000001853c44c58f:   je      1853c44c60ch\n" +
                "  0x000001853c44c595:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c59f:   inc     dword ptr [rax+158h]        ;*ifeq {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - java.lang.String::coder@3 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "  0x000001853c44c5a5:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c5af:   inc     dword ptr [rax+168h]        ;*goto {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - java.lang.String::coder@10 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "  0x000001853c44c5b5:   mov     eax,0eh\n" +
                "  0x000001853c44c5ba:   add     rsp,40h\n" +
                "  0x000001853c44c5be:   pop     rbp\n" +
                "  0x000001853c44c5bf:   mov     r10,qword ptr [r15+108h]\n" +
                "  0x000001853c44c5c6:   test    dword ptr [r10],eax         ;   {poll_return}\n" +
                "  0x000001853c44c5c9:   ret\n" +
                "  0x000001853c44c5ca:   mov     r10,1876270fbe0h            ;   {metadata({method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c5d4:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c5d9:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c5e1:   call    1853be6b980h                ; ImmutableOopMap {rdx=Oop }\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@-1 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c5e6:   jmp     1853c44c514h\n" +
                "  0x000001853c44c5eb:   mov     r10,18760db7a48h            ;   {metadata({method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c5f5:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c5fa:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c602:   call    1853be6b980h                ; ImmutableOopMap {rax=Oop }\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - java.lang.String::length@-1 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c607:   jmp     1853c44c55bh\n" +
                "  0x000001853c44c60c:   mov     r10,18760dbd228h            ;   {metadata({method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c616:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c61b:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c623:   call    1853be6b980h                ; ImmutableOopMap {}\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - java.lang.String::coder@-1 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c628:   jmp     1853c44c595h\n" +
                "  0x000001853c44c62d:   nop\n" +
                "  0x000001853c44c62e:   nop\n" +
                "  0x000001853c44c62f:   mov     rax,qword ptr [r15+3f0h]\n" +
                "  0x000001853c44c636:   mov     r10,0h\n" +
                "  0x000001853c44c640:   mov     qword ptr [r15+3f0h],r10\n" +
                "  0x000001853c44c647:   mov     r10,0h\n" +
                "  0x000001853c44c651:   mov     qword ptr [r15+3f8h],r10\n" +
                "  0x000001853c44c658:   add     rsp,40h\n" +
                "  0x000001853c44c65c:   pop     rbp\n" +
                "  0x000001853c44c65d:   jmp     1853bdd7d00h                ;   {runtime_call unwind_exception Runtime1 stub}\n" +
                "  0x000001853c44c662:   hlt\n" +
                "  0x000001853c44c663:   hlt\n" +
                "  0x000001853c44c664:   hlt\n" +
                "  0x000001853c44c665:   hlt\n" +
                "  0x000001853c44c666:   hlt\n" +
                "  0x000001853c44c667:   hlt\n" +
                "  0x000001853c44c668:   hlt\n" +
                "  0x000001853c44c669:   hlt\n" +
                "  0x000001853c44c66a:   hlt\n" +
                "  0x000001853c44c66b:   hlt\n" +
                "  0x000001853c44c66c:   hlt\n" +
                "  0x000001853c44c66d:   hlt\n" +
                "  0x000001853c44c66e:   hlt\n" +
                "  0x000001853c44c66f:   hlt\n" +
                "  0x000001853c44c670:   hlt\n" +
                "  0x000001853c44c671:   hlt\n" +
                "  0x000001853c44c672:   hlt\n" +
                "  0x000001853c44c673:   hlt\n" +
                "  0x000001853c44c674:   hlt\n" +
                "  0x000001853c44c675:   hlt\n" +
                "  0x000001853c44c676:   hlt\n" +
                "  0x000001853c44c677:   hlt\n" +
                "  0x000001853c44c678:   hlt\n" +
                "  0x000001853c44c679:   hlt\n" +
                "  0x000001853c44c67a:   hlt\n" +
                "  0x000001853c44c67b:   hlt\n" +
                "  0x000001853c44c67c:   hlt\n" +
                "  0x000001853c44c67d:   hlt\n" +
                "  0x000001853c44c67e:   hlt\n" +
                "  0x000001853c44c67f:   hlt\n" +
                "[Exception Handler]\n" +
                "  0x000001853c44c680:   call    1853be67a80h                ;   {no_reloc}\n" +
                "  0x000001853c44c685:   mov     qword ptr [rsp+0ffffffffffffffd8h],rsp\n" +
                "  0x000001853c44c68a:   sub     rsp,80h\n" +
                "  0x000001853c44c691:   mov     qword ptr [rsp+78h],rax\n" +
                "  0x000001853c44c696:   mov     qword ptr [rsp+70h],rcx\n" +
                "  0x000001853c44c69b:   mov     qword ptr [rsp+68h],rdx\n" +
                "  0x000001853c44c6a0:   mov     qword ptr [rsp+60h],rbx\n" +
                "  0x000001853c44c6a5:   mov     qword ptr [rsp+50h],rbp\n" +
                "  0x000001853c44c6aa:   mov     qword ptr [rsp+48h],rsi\n" +
                "  0x000001853c44c6af:   mov     qword ptr [rsp+40h],rdi\n" +
                "  0x000001853c44c6b4:   mov     qword ptr [rsp+38h],r8\n" +
                "  0x000001853c44c6b9:   mov     qword ptr [rsp+30h],r9\n" +
                "  0x000001853c44c6be:   mov     qword ptr [rsp+28h],r10\n" +
                "  0x000001853c44c6c3:   mov     qword ptr [rsp+20h],r11\n" +
                "  0x000001853c44c6c8:   mov     qword ptr [rsp+18h],r12\n" +
                "  0x000001853c44c6cd:   mov     qword ptr [rsp+10h],r13\n" +
                "  0x000001853c44c6d2:   mov     qword ptr [rsp+8h],r14\n" +
                "  0x000001853c44c6d7:   mov     qword ptr [rsp],r15\n" +
                "  0x000001853c44c6db:   mov     rcx,7ffd933cc598h           ;   {external_word}\n" +
                "  0x000001853c44c6e5:   mov     rdx,1853c44c685h            ;   {internal_word}\n" +
                "  0x000001853c44c6ef:   mov     r8,rsp\n" +
                "  0x000001853c44c6f2:   and     rsp,0fffffffffffffff0h\n" +
                "  0x000001853c44c6f6:   mov     r10,7ffd930ed140h           ;   {runtime_call}\n" +
                "  0x000001853c44c700:   call indirect r10\n" +
                "  0x000001853c44c703:   hlt\n" +
                "[Deopt Handler Code]\n" +
                "  0x000001853c44c704:   mov     r10,1853c44c704h            ;   {section_word}\n" +
                "  0x000001853c44c70e:   push    r10\n" +
                "  0x000001853c44c710:   jmp     1853bdb93a0h                ;   {runtime_call DeoptimizationBlob}\n" +
                "  0x000001853c44c715:   hlt\n" +
                "  0x000001853c44c716:   hlt\n" +
                "  0x000001853c44c717:   hlt\n" +
                "--------------------------------------------------------------------------------\n" +
                "\n" +
                "============================= C2-compiled nmethod ==============================\n" +
                "----------------------------------- Assembly -----------------------------------\n" +
                "\n" +
                "Compiled method (c2)     456  691       4       com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen (6 bytes)\n" +
                " total in heap  [0x0000018543883590,0x00000185438837c8] = 568\n" +
                " relocation     [0x00000185438836f8,0x0000018543883708] = 16\n" +
                " main code      [0x0000018543883720,0x0000018543883760] = 64\n" +
                " stub code      [0x0000018543883760,0x0000018543883778] = 24\n" +
                " oops           [0x0000018543883778,0x0000018543883780] = 8\n" +
                " metadata       [0x0000018543883780,0x0000018543883788] = 8\n" +
                " scopes data    [0x0000018543883788,0x0000018543883790] = 8\n" +
                " scopes pcs     [0x0000018543883790,0x00000185438837c0] = 48\n" +
                " dependencies   [0x00000185438837c0,0x00000185438837c8] = 8\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "[Constant Pool (empty)]\n" +
                "\n" +
                "--------------------------------------------------------------------------------\n" +
                "\n" +
                "Argument 0 is unknown.RIP: 0x18543883720 Code size: 0x00000058\n" +
                "[Entry Point]\n" +
                "  # {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding'\n" +
                "  #           [sp+0x20]  (sp of caller)\n" +
                "  0x0000018543883720:   cmp     rax,qword ptr [rdx+8h]\n" +
                "  0x0000018543883724:   jne     1853bdbb000h                ;   {runtime_call ic_miss_stub}\n" +
                "  0x000001854388372a:   nop\n" +
                "  0x000001854388372c:   nop     dword ptr [rax+0h]\n" +
                "[Verified Entry Point]\n" +
                "  0x0000018543883730:   sub     rsp,18h\n" +
                "  0x0000018543883737:   mov     qword ptr [rsp+10h],rbp     ;*synchronization entry\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@-1 (line 19)\n" +
                "  0x000001854388373c:   mov     eax,0eh\n" +
                "  0x0000018543883741:   add     rsp,10h\n" +
                "  0x0000018543883745:   pop     rbp\n" +
                "  0x0000018543883746:   mov     r10,qword ptr [r15+108h]\n" +
                "  0x000001854388374d:   test    dword ptr [r10],eax         ;   {poll_return}\n" +
                "  0x0000018543883750:   ret\n" +
                "  0x0000018543883751:   hlt\n" +
                "  0x0000018543883752:   hlt\n" +
                "  0x0000018543883753:   hlt\n" +
                "  0x0000018543883754:   hlt\n" +
                "  0x0000018543883755:   hlt\n" +
                "  0x0000018543883756:   hlt\n" +
                "  0x0000018543883757:   hlt\n" +
                "  0x0000018543883758:   hlt\n" +
                "  0x0000018543883759:   hlt\n" +
                "  0x000001854388375a:   hlt\n" +
                "  0x000001854388375b:   hlt\n" +
                "  0x000001854388375c:   hlt\n" +
                "  0x000001854388375d:   hlt\n" +
                "  0x000001854388375e:   hlt\n" +
                "  0x000001854388375f:   hlt\n" +
                "[Exception Handler]\n" +
                "  0x0000018543883760:   jmp     1853be65080h                ;   {no_reloc}\n" +
                "[Deopt Handler Code]\n" +
                "  0x0000018543883765:   call    1854388376ah\n" +
                "  0x000001854388376a:   sub     qword ptr [rsp],5h\n" +
                "  0x000001854388376f:   jmp     1853bdb93a0h                ;   {runtime_call DeoptimizationBlob}\n" +
                "  0x0000018543883774:   hlt\n" +
                "  0x0000018543883775:   hlt\n" +
                "  0x0000018543883776:   hlt\n" +
                "  0x0000018543883777:   hlt\n" +
                "--------------------------------------------------------------------------------\n" +
                "4,377 ns/op\n" +
                "# Warmup Iteration   2: 3,253 ns/op\n" +
                "# Warmup Iteration   3: 3,838 ns/op\n" +
                "# Warmup Iteration   4: 3,238 ns/op\n" +
                "# Warmup Iteration   5: 2,676 ns/op\n" +
                "Iteration   1: 3,369 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   2: 2,839 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   3: 2,637 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   4: 2,729 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "Iteration   5: 2,464 ns/op\n" +
                "                 ·gc.alloc.rate:      0,001 MB/sec\n" +
                "                 ·gc.alloc.rate.norm: ≈ 10⁻⁶ B/op\n" +
                "                 ·gc.count:           ≈ 0 counts\n" +
                "\n" +
                "\n" +
                "\n" +
                "Result \"returnStringLen\":\n" +
                "  2,808 ±(99.9%) 1,319 ns/op [Average]\n" +
                "  (min, avg, max) = (2,464, 2,808, 3,369), stdev = 0,343\n" +
                "  CI (99.9%): [1,488, 4,127] (assumes normal distribution)\n" +
                "\n" +
                "Secondary result \"·gc.alloc.rate\":\n" +
                "  0,001 ±(99.9%) 0,001 MB/sec [Average]\n" +
                "  (min, avg, max) = (0,001, 0,001, 0,001), stdev = 0,001\n" +
                "  CI (99.9%): [0,001, 0,001] (assumes normal distribution)\n" +
                "\n" +
                "Secondary result \"·gc.alloc.rate.norm\":\n" +
                "  ≈ 10⁻⁶ B/op\n" +
                "\n" +
                "Secondary result \"·gc.count\":\n" +
                "  ≈ 0 counts\n" +
                "\n" +
                "\n" +
                "# Run complete. Total time: 00:00:21\n" +
                "\n" +
                "Benchmark                                                     Mode  Cnt   Score    Error   Units\n" +
                "StringConstandFolding.returnStringItself                      avgt    5   3,411 ±  0,742   ns/op\n" +
                "StringConstandFolding.returnStringItself:·gc.alloc.rate       avgt    5   0,001 ±  0,001  MB/sec\n" +
                "StringConstandFolding.returnStringItself:·gc.alloc.rate.norm  avgt    5  ≈ 10⁻⁶             B/op\n" +
                "StringConstandFolding.returnStringItself:·gc.count            avgt    5     ≈ 0           counts\n" +
                "StringConstandFolding.returnStringLen                         avgt    5   2,808 ±  1,319   ns/op\n" +
                "StringConstandFolding.returnStringLen:·gc.alloc.rate          avgt    5   0,001 ±  0,001  MB/sec\n" +
                "StringConstandFolding.returnStringLen:·gc.alloc.rate.norm     avgt    5  ≈ 10⁻⁶             B/op\n" +
                "StringConstandFolding.returnStringLen:·gc.count               avgt    5     ≈ 0           counts\n" +
                "\n" +
                "Process finished with exit code 0\n";

        var extractor = new OpenJdkWindowsAssemblyCodeExtractor(exampleSysOutOfBenchmark);

        List<AssemblyCode> c1Methods = extractor.extractC1Compiled();

        assertEquals(2, c1Methods.size());
        assertEquals(new AssemblyCode("com.chibik.perf.asm.constfold.StringConstandFolding", "returnStringItself", "  0x00000214965630c0:   mov     dword ptr [rsp+0ffffffffffff9000h],eax\n" +
                "  0x00000214965630c7:   push    rbp\n" +
                "  0x00000214965630c8:   sub     rsp,30h                     ;*ldc {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringItself@0 (line 24)\n" +
                "  0x00000214965630cc:   mov     rax,214ad5cc0f8h            ;   {oop(\"asdfgdshjffasd\"{0x00000214ad5cc0f8})}\n" +
                "  0x00000214965630d6:   add     rsp,30h\n" +
                "  0x00000214965630da:   pop     rbp\n" +
                "  0x00000214965630db:   mov     r10,qword ptr [r15+108h]\n" +
                "  0x00000214965630e2:   test    dword ptr [r10],eax         ;   {poll_return}\n" +
                "  0x00000214965630e5:   ret\n" +
                "  0x00000214965630e6:   nop\n" +
                "  0x00000214965630e7:   nop\n" +
                "  0x00000214965630e8:   mov     rax,qword ptr [r15+3f0h]\n" +
                "  0x00000214965630ef:   mov     r10,0h\n" +
                "  0x00000214965630f9:   mov     qword ptr [r15+3f0h],r10\n" +
                "  0x0000021496563100:   mov     r10,0h\n" +
                "  0x000002149656310a:   mov     qword ptr [r15+3f8h],r10\n" +
                "  0x0000021496563111:   add     rsp,30h\n" +
                "  0x0000021496563115:   pop     rbp\n" +
                "  0x0000021496563116:   jmp     2148eab7d00h                ;   {runtime_call unwind_exception Runtime1 stub}\n" +
                "  0x000002149656311b:   hlt\n" +
                "  0x000002149656311c:   hlt\n" +
                "  0x000002149656311d:   hlt\n" +
                "  0x000002149656311e:   hlt\n" +
                "  0x000002149656311f:   hlt\n" +
                "[Exception Handler]\n" +
                "  0x0000021496563120:   call    2148eb47a80h                ;   {no_reloc}\n" +
                "  0x0000021496563125:   mov     qword ptr [rsp+0ffffffffffffffd8h],rsp\n" +
                "  0x000002149656312a:   sub     rsp,80h\n" +
                "  0x0000021496563131:   mov     qword ptr [rsp+78h],rax\n" +
                "  0x0000021496563136:   mov     qword ptr [rsp+70h],rcx\n" +
                "  0x000002149656313b:   mov     qword ptr [rsp+68h],rdx\n" +
                "  0x0000021496563140:   mov     qword ptr [rsp+60h],rbx\n" +
                "  0x0000021496563145:   mov     qword ptr [rsp+50h],rbp\n" +
                "  0x000002149656314a:   mov     qword ptr [rsp+48h],rsi\n" +
                "  0x000002149656314f:   mov     qword ptr [rsp+40h],rdi\n" +
                "  0x0000021496563154:   mov     qword ptr [rsp+38h],r8\n" +
                "  0x0000021496563159:   mov     qword ptr [rsp+30h],r9\n" +
                "  0x000002149656315e:   mov     qword ptr [rsp+28h],r10\n" +
                "  0x0000021496563163:   mov     qword ptr [rsp+20h],r11\n" +
                "  0x0000021496563168:   mov     qword ptr [rsp+18h],r12\n" +
                "  0x000002149656316d:   mov     qword ptr [rsp+10h],r13\n" +
                "  0x0000021496563172:   mov     qword ptr [rsp+8h],r14\n" +
                "  0x0000021496563177:   mov     qword ptr [rsp],r15\n" +
                "  0x000002149656317b:   mov     rcx,7ffd933cc598h           ;   {external_word}\n" +
                "  0x0000021496563185:   mov     rdx,21496563125h            ;   {internal_word}\n" +
                "  0x000002149656318f:   mov     r8,rsp\n" +
                "  0x0000021496563192:   and     rsp,0fffffffffffffff0h\n" +
                "  0x0000021496563196:   mov     r10,7ffd930ed140h           ;   {runtime_call}\n" +
                "  0x00000214965631a0:   call indirect r10\n" +
                "  0x00000214965631a3:   hlt\n" +
                "[Deopt Handler Code]\n" +
                "  0x00000214965631a4:   mov     r10,214965631a4h            ;   {section_word}\n" +
                "  0x00000214965631ae:   push    r10\n" +
                "  0x00000214965631b0:   jmp     2148ea993a0h                ;   {runtime_call DeoptimizationBlob}\n" +
                "  0x00000214965631b5:   hlt\n" +
                "  0x00000214965631b6:   hlt\n" +
                "  0x00000214965631b7:   hlt\n"), c1Methods.get(0));
        assertEquals(new AssemblyCode("com.chibik.perf.asm.constfold.StringConstandFolding", "returnStringLen", "  0x000001853c44c4e0:   mov     dword ptr [rsp+0ffffffffffff9000h],eax\n" +
                "  0x000001853c44c4e7:   push    rbp\n" +
                "  0x000001853c44c4e8:   sub     rsp,40h\n" +
                "  0x000001853c44c4ec:   mov     rax,187627c5dc0h            ;   {metadata(method data for {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c4f6:   mov     esi,dword ptr [rax+104h]\n" +
                "  0x000001853c44c4fc:   add     esi,8h\n" +
                "  0x000001853c44c4ff:   mov     dword ptr [rax+104h],esi\n" +
                "  0x000001853c44c505:   and     esi,1ff8h\n" +
                "  0x000001853c44c50b:   cmp     esi,0h\n" +
                "  0x000001853c44c50e:   je      1853c44c5cah                ;*ldc {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@0 (line 19)\n" +
                "  0x000001853c44c514:   mov     rax,1855a7c5f28h            ;   {oop(\"asdfgdshjffasd\"{0x000001855a7c5f28})}\n" +
                "  0x000001853c44c51e:   mov     rsi,rax\n" +
                "  0x000001853c44c521:   mov     rdi,187627c5dc0h            ;   {metadata(method data for {method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c52b:   add     qword ptr [rdi+148h],1h\n" +
                "  0x000001853c44c533:   mov     rsi,1876106c398h            ;   {metadata(method data for {method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c53d:   mov     edi,dword ptr [rsi+104h]\n" +
                "  0x000001853c44c543:   add     edi,8h\n" +
                "  0x000001853c44c546:   mov     dword ptr [rsi+104h],edi\n" +
                "  0x000001853c44c54c:   and     edi,7ffff8h\n" +
                "  0x000001853c44c552:   cmp     edi,0h\n" +
                "  0x000001853c44c555:   je      1853c44c5ebh\n" +
                "  0x000001853c44c55b:   mov     rsi,1876106c398h            ;   {metadata(method data for {method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c565:   add     qword ptr [rsi+148h],1h\n" +
                "  0x000001853c44c56d:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c577:   mov     esi,dword ptr [rax+104h]\n" +
                "  0x000001853c44c57d:   add     esi,8h\n" +
                "  0x000001853c44c580:   mov     dword ptr [rax+104h],esi\n" +
                "  0x000001853c44c586:   and     esi,7ffff8h\n" +
                "  0x000001853c44c58c:   cmp     esi,0h\n" +
                "  0x000001853c44c58f:   je      1853c44c60ch\n" +
                "  0x000001853c44c595:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c59f:   inc     dword ptr [rax+158h]        ;*ifeq {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - java.lang.String::coder@3 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "  0x000001853c44c5a5:   mov     rax,1876106c1e8h            ;   {metadata(method data for {method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c5af:   inc     dword ptr [rax+168h]        ;*goto {reexecute=0 rethrow=0 return_oop=0}\n" +
                "                                                            ; - java.lang.String::coder@10 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "  0x000001853c44c5b5:   mov     eax,0eh\n" +
                "  0x000001853c44c5ba:   add     rsp,40h\n" +
                "  0x000001853c44c5be:   pop     rbp\n" +
                "  0x000001853c44c5bf:   mov     r10,qword ptr [r15+108h]\n" +
                "  0x000001853c44c5c6:   test    dword ptr [r10],eax         ;   {poll_return}\n" +
                "  0x000001853c44c5c9:   ret\n" +
                "  0x000001853c44c5ca:   mov     r10,1876270fbe0h            ;   {metadata({method} {0x000001876270fbe8} 'returnStringLen' '()I' in 'com/chibik/perf/asm/constfold/StringConstandFolding')}\n" +
                "  0x000001853c44c5d4:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c5d9:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c5e1:   call    1853be6b980h                ; ImmutableOopMap {rdx=Oop }\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@-1 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c5e6:   jmp     1853c44c514h\n" +
                "  0x000001853c44c5eb:   mov     r10,18760db7a48h            ;   {metadata({method} {0x0000018760db7a50} 'length' '()I' in 'java/lang/String')}\n" +
                "  0x000001853c44c5f5:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c5fa:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c602:   call    1853be6b980h                ; ImmutableOopMap {rax=Oop }\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - java.lang.String::length@-1 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c607:   jmp     1853c44c55bh\n" +
                "  0x000001853c44c60c:   mov     r10,18760dbd228h            ;   {metadata({method} {0x0000018760dbd230} 'coder' '()B' in 'java/lang/String')}\n" +
                "  0x000001853c44c616:   mov     qword ptr [rsp+8h],r10\n" +
                "  0x000001853c44c61b:   mov     qword ptr [rsp],0ffffffffffffffffh\n" +
                "  0x000001853c44c623:   call    1853be6b980h                ; ImmutableOopMap {}\n" +
                "                                                            ;*synchronization entry\n" +
                "                                                            ; - java.lang.String::coder@-1 (line 3659)\n" +
                "                                                            ; - java.lang.String::length@6 (line 674)\n" +
                "                                                            ; - com.chibik.perf.asm.constfold.StringConstandFolding::returnStringLen@2 (line 19)\n" +
                "                                                            ;   {runtime_call counter_overflow Runtime1 stub}\n" +
                "  0x000001853c44c628:   jmp     1853c44c595h\n" +
                "  0x000001853c44c62d:   nop\n" +
                "  0x000001853c44c62e:   nop\n" +
                "  0x000001853c44c62f:   mov     rax,qword ptr [r15+3f0h]\n" +
                "  0x000001853c44c636:   mov     r10,0h\n" +
                "  0x000001853c44c640:   mov     qword ptr [r15+3f0h],r10\n" +
                "  0x000001853c44c647:   mov     r10,0h\n" +
                "  0x000001853c44c651:   mov     qword ptr [r15+3f8h],r10\n" +
                "  0x000001853c44c658:   add     rsp,40h\n" +
                "  0x000001853c44c65c:   pop     rbp\n" +
                "  0x000001853c44c65d:   jmp     1853bdd7d00h                ;   {runtime_call unwind_exception Runtime1 stub}\n" +
                "  0x000001853c44c662:   hlt\n" +
                "  0x000001853c44c663:   hlt\n" +
                "  0x000001853c44c664:   hlt\n" +
                "  0x000001853c44c665:   hlt\n" +
                "  0x000001853c44c666:   hlt\n" +
                "  0x000001853c44c667:   hlt\n" +
                "  0x000001853c44c668:   hlt\n" +
                "  0x000001853c44c669:   hlt\n" +
                "  0x000001853c44c66a:   hlt\n" +
                "  0x000001853c44c66b:   hlt\n" +
                "  0x000001853c44c66c:   hlt\n" +
                "  0x000001853c44c66d:   hlt\n" +
                "  0x000001853c44c66e:   hlt\n" +
                "  0x000001853c44c66f:   hlt\n" +
                "  0x000001853c44c670:   hlt\n" +
                "  0x000001853c44c671:   hlt\n" +
                "  0x000001853c44c672:   hlt\n" +
                "  0x000001853c44c673:   hlt\n" +
                "  0x000001853c44c674:   hlt\n" +
                "  0x000001853c44c675:   hlt\n" +
                "  0x000001853c44c676:   hlt\n" +
                "  0x000001853c44c677:   hlt\n" +
                "  0x000001853c44c678:   hlt\n" +
                "  0x000001853c44c679:   hlt\n" +
                "  0x000001853c44c67a:   hlt\n" +
                "  0x000001853c44c67b:   hlt\n" +
                "  0x000001853c44c67c:   hlt\n" +
                "  0x000001853c44c67d:   hlt\n" +
                "  0x000001853c44c67e:   hlt\n" +
                "  0x000001853c44c67f:   hlt\n" +
                "[Exception Handler]\n" +
                "  0x000001853c44c680:   call    1853be67a80h                ;   {no_reloc}\n" +
                "  0x000001853c44c685:   mov     qword ptr [rsp+0ffffffffffffffd8h],rsp\n" +
                "  0x000001853c44c68a:   sub     rsp,80h\n" +
                "  0x000001853c44c691:   mov     qword ptr [rsp+78h],rax\n" +
                "  0x000001853c44c696:   mov     qword ptr [rsp+70h],rcx\n" +
                "  0x000001853c44c69b:   mov     qword ptr [rsp+68h],rdx\n" +
                "  0x000001853c44c6a0:   mov     qword ptr [rsp+60h],rbx\n" +
                "  0x000001853c44c6a5:   mov     qword ptr [rsp+50h],rbp\n" +
                "  0x000001853c44c6aa:   mov     qword ptr [rsp+48h],rsi\n" +
                "  0x000001853c44c6af:   mov     qword ptr [rsp+40h],rdi\n" +
                "  0x000001853c44c6b4:   mov     qword ptr [rsp+38h],r8\n" +
                "  0x000001853c44c6b9:   mov     qword ptr [rsp+30h],r9\n" +
                "  0x000001853c44c6be:   mov     qword ptr [rsp+28h],r10\n" +
                "  0x000001853c44c6c3:   mov     qword ptr [rsp+20h],r11\n" +
                "  0x000001853c44c6c8:   mov     qword ptr [rsp+18h],r12\n" +
                "  0x000001853c44c6cd:   mov     qword ptr [rsp+10h],r13\n" +
                "  0x000001853c44c6d2:   mov     qword ptr [rsp+8h],r14\n" +
                "  0x000001853c44c6d7:   mov     qword ptr [rsp],r15\n" +
                "  0x000001853c44c6db:   mov     rcx,7ffd933cc598h           ;   {external_word}\n" +
                "  0x000001853c44c6e5:   mov     rdx,1853c44c685h            ;   {internal_word}\n" +
                "  0x000001853c44c6ef:   mov     r8,rsp\n" +
                "  0x000001853c44c6f2:   and     rsp,0fffffffffffffff0h\n" +
                "  0x000001853c44c6f6:   mov     r10,7ffd930ed140h           ;   {runtime_call}\n" +
                "  0x000001853c44c700:   call indirect r10\n" +
                "  0x000001853c44c703:   hlt\n" +
                "[Deopt Handler Code]\n" +
                "  0x000001853c44c704:   mov     r10,1853c44c704h            ;   {section_word}\n" +
                "  0x000001853c44c70e:   push    r10\n" +
                "  0x000001853c44c710:   jmp     1853bdb93a0h                ;   {runtime_call DeoptimizationBlob}\n" +
                "  0x000001853c44c715:   hlt\n" +
                "  0x000001853c44c716:   hlt\n" +
                "  0x000001853c44c717:   hlt\n"), c1Methods.get(1));
    }
}