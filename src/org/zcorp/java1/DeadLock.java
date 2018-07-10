package org.zcorp.java1;

public class DeadLock {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    private static volatile boolean canRun = false;

    private static void block_1_2() {
        synchronized (LOCK_1) {
            canRun = true;
            System.out.println("method 'block_1_2' before 'synchronized (LOCK_2)'");
            synchronized (LOCK_2) {
                System.out.println("method 'block_1_2' after 'synchronized (LOCK_2)'");
            }
        }
    }

    private static void block_2_1(Thread other) {
        synchronized (LOCK_2) {
            while (!canRun) {
                if (other.getState() == Thread.State.NEW) {
                    other.start();
                }
            }
            System.out.println("method 'block_2_1' before 'synchronized (LOCK_1)'");
            synchronized (LOCK_1) {
                System.out.println("method 'block_2_1' after 'synchronized (LOCK_1)'");
            }
        }
    }

    public static void main(String[] args) {
        Thread block_1_2_thread = new Thread(DeadLock::block_1_2);
        Thread block_2_1_thread = new Thread(() -> block_2_1(block_1_2_thread));
        block_2_1_thread.start();
    }

}