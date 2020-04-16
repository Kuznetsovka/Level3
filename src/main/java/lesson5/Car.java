package lesson5;

import static lesson5.MainClass.winner;

public class Car{

    private int number;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, int number) {
        this.race = race;
        this.speed = speed;
        this.number = number;
        this.name = "Участник #" + number;
    }

    public void infoReady(){
        System.out.println(this.name + " готовится");
        try {
            Thread.sleep(500 + (int)(Math.random() * 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " готов");

    }

    public void go(){
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go((this));
        }
        winner.getAndSet(number);
    }
}
