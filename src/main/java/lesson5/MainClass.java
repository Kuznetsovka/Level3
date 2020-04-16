package lesson5;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {
    public static final int CARS_COUNT = 4;
    protected static AtomicInteger winner = new AtomicInteger(0);
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        ArrayList<Car> cars = new ArrayList<>(CARS_COUNT);
        for (int i = 0; i < CARS_COUNT; i++) {
            cars.add( new Car(race, 20 + (int) (Math.random() * 10),i+1));
        }

        ExecutorService pool = Executors.newFixedThreadPool(CARS_COUNT);

        cars.forEach(car -> pool.submit(car::infoReady));
        try {
            pool.awaitTermination(2000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        cars.forEach(car -> pool.submit(car::go));

        do{
            if (winner.get()!=0)
                System.out.println("Участник #" + winner.get() + " - WIN");
        } while ( winner.get()==0);

        pool.shutdown();

        try {
            pool.awaitTermination(1,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

    }
}

