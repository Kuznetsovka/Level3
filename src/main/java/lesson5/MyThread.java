package lesson5;

import static lesson5.MainClass.CARS_COUNT;

public class MyThread extends Thread {
    Car car;
    Object mon = new Object();
    public MyThread(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            synchronized (mon) {
                car.infoReady();
            }
            Thread.sleep(500 + (int) (Math.random() * 800));
            synchronized (mon) {
                car.infoReadyDone();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //car.start();
    }
}
