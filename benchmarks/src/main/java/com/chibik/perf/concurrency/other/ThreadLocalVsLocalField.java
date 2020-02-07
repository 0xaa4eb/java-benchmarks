package com.chibik.perf.concurrency.other;

public class ThreadLocalVsLocalField {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static class CustomThread extends Thread {

        private int localField;

        public CustomThread(Runnable target) {
            super(target);
        }

        public int getLocalField() {
            return localField;
        }

        public void setLocalField(int localField) {
            this.localField = localField;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CustomThread t = new CustomThread(
                () -> {
                    long start = System.currentTimeMillis();

                    for(int i = 0; i < 100000000; i++) {
                        threadLocal.set(i);
                    }

                    System.out.println(System.currentTimeMillis() - start);

                    start = System.currentTimeMillis();

                    CustomThread ct = (CustomThread) Thread.currentThread();

                    for(int i = 0; i < 100000000; i++) {
                        ct.setLocalField(i);
                    }

                    System.out.println(System.currentTimeMillis() - start);
                }
        );

        t.start();
        t.join();

        System.out.println();
        System.out.println(t.getLocalField());

    }
}
