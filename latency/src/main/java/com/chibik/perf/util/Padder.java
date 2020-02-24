package com.chibik.perf.util;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Padder {
    long head1;
    long head2;
    long head3;
    long head4;
    long head5;
    long head6;
    long head7;
    long head8;
    long head9;
    long head10;
    long head11;
    long head12;
    long head13;
    long head14;
    long head15;
    long head16;
    long head17;
    long head18;
    long head19;
    long head20;
    long head21;
    long head22;
    long head23;
    long head24;
    long head25;
    long head26;
    long head27;
    long head28;
    long head29;
    long head30;
    long head31;
    long head32;


    public Padder() {
        head1 = ThreadLocalRandom.current().nextInt();
        head2 = ThreadLocalRandom.current().nextInt();
        head3 = ThreadLocalRandom.current().nextInt();
        head4 = ThreadLocalRandom.current().nextInt();
        head5 = ThreadLocalRandom.current().nextInt();
        head6 = ThreadLocalRandom.current().nextInt();
        head7 = ThreadLocalRandom.current().nextInt();
        head8 = ThreadLocalRandom.current().nextInt();
        head9 = ThreadLocalRandom.current().nextInt();
        head10 = ThreadLocalRandom.current().nextInt();
        head11 = ThreadLocalRandom.current().nextInt();
        head12 = ThreadLocalRandom.current().nextInt();
        head13 = ThreadLocalRandom.current().nextInt();
        head14 = ThreadLocalRandom.current().nextInt();
        head15 = ThreadLocalRandom.current().nextInt();
        head16 = ThreadLocalRandom.current().nextInt();
        head17 = ThreadLocalRandom.current().nextInt();
        head18 = ThreadLocalRandom.current().nextInt();
        head19 = ThreadLocalRandom.current().nextInt();
        head20 = ThreadLocalRandom.current().nextInt();
        head21 = ThreadLocalRandom.current().nextInt();
        head22 = ThreadLocalRandom.current().nextInt();
        head23 = ThreadLocalRandom.current().nextInt();
        head24 = ThreadLocalRandom.current().nextInt();
        head25 = ThreadLocalRandom.current().nextInt();
        head26 = ThreadLocalRandom.current().nextInt();
        head27 = ThreadLocalRandom.current().nextInt();
        head28 = ThreadLocalRandom.current().nextInt();
        head29 = ThreadLocalRandom.current().nextInt();
        head30 = ThreadLocalRandom.current().nextInt();
        head31 = ThreadLocalRandom.current().nextInt();
        head32 = ThreadLocalRandom.current().nextInt();
    }

    public long getHeadXored() {
        return head1 ^ head2 ^ head3 ^ head4 ^ head5 ^ head6 ^ head7 ^ head8 ^ head9 ^ head10 ^ head11 ^
                head12 ^ head13 ^ head14 ^ head15 ^ head16 ^ head17 ^ head18 ^ head19 ^ head20 ^ head21 ^
                head22 ^ head23 ^ head24 ^ head25 ^ head26 ^ head27 ^ head28 ^ head29 ^ head30 ^ head31 ^ head32;
    }

    @Override
    public String toString() {
        return "Padder{" +
                "head1=" + head1 +
                ", head2=" + head2 +
                ", head3=" + head3 +
                ", head4=" + head4 +
                ", head5=" + head5 +
                ", head6=" + head6 +
                ", head7=" + head7 +
                ", head8=" + head8 +
                ", head9=" + head9 +
                ", head10=" + head10 +
                ", head11=" + head11 +
                ", head12=" + head12 +
                ", head13=" + head13 +
                ", head14=" + head14 +
                ", head15=" + head15 +
                ", head16=" + head16 +
                ", head17=" + head17 +
                ", head18=" + head18 +
                ", head19=" + head19 +
                ", head20=" + head20 +
                ", head21=" + head21 +
                ", head22=" + head22 +
                ", head23=" + head23 +
                ", head24=" + head24 +
                ", head25=" + head25 +
                ", head26=" + head26 +
                ", head27=" + head27 +
                ", head28=" + head28 +
                ", head29=" + head29 +
                ", head30=" + head30 +
                ", head31=" + head31 +
                ", head32=" + head32 +
                '}';
    }
}
