package lesson1;

import java.util.ArrayList;

public class lesson1 {
    public static void main(String[] args) {
        //Первые 2 задачи написал до начала урока и старался наоборот везде использовать дженерики, понимаю, что здесь можно и без них
        ClassArray<String> arrExample1 = new ClassArray<String>("0","1","2","3","4");
        Object obj1 = new Object();
        Object obj2 = new Object();
        ClassArray<Object> arrExample2 = new ClassArray<Object>(obj1, obj2);
        arrExample1.replaceIndex(2, 4);
        arrExample2.replaceIndex(0, 1);
        System.out.println( arrExample1 );
        System.out.println( arrExample2 ); //Просто для проверки
        ArrayList<String> arrList = arrExample1.convertToArrayList();
        for (Object o : arrList) {
            System.out.println(o);
        }
        System.out.println( arrList.getClass());
        System.out.println( "----------------");
        Box<Orange> boxOrange = new Box<Orange>();
        Box<Apple> boxApple1 = new Box<Apple>();
        Box<Apple> boxApple2 = new Box<Apple>();
        for (int i = 0; i < 2; i++) {
            boxApple1.addFruit( new Apple() );
            boxApple2.addFruit( new Apple() );
        }
        for (int i = 0; i < 3; i++) {
            boxOrange.addFruit( new Orange() );
        }
        boxApple1.infoCompare(boxOrange);
        boxApple1.infoCompare(boxApple2);
        boxApple1.pourInBox(boxApple2);
        System.out.println("boxApple1: " + boxApple1.infoBox());
        System.out.println("boxApple2: " + boxApple2.infoBox());
        System.out.println("boxOrange: " + boxOrange.infoBox());
    }

}