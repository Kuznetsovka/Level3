package lesson5;

import java.util.concurrent.Semaphore;

public class MFU {

    final Object printLock = new Object();
    final Object scanLock = new Object();
    Semaphore sem = new Semaphore(1);


    public void print(String doc, int n) {
        synchronized (printLock) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Начало печати");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Конец печати");
            sem.release();
        }
    }

    public void scan(String doc, int n, Boolean isPrint) {
        synchronized (scanLock) {

            System.out.println("Начало сканирования");

            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Конец сканирования");
            if (isPrint) print(doc, n);
        }
    }

    public static void main(String[] args) {
        final MFU mfu = new MFU();

        new Thread(() -> mfu.print("Doc 1", 10)).start();
        new Thread(() -> mfu.scan("Doc 4", 5,true)).start();
        new Thread(() -> mfu.print("Doc 2", 5)).start();
        new Thread(() -> mfu.scan("Doc 3", 5,false)).start();

    }

}