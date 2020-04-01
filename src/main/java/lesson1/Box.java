package lesson1;
import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;
    public Box() {
        this.fruits = new ArrayList<T>();
    }

    public float getWeight() {
        float weight = 0;
        for (int i = 0; i < fruits.size(); i++) {
            weight += fruits.get(i).getWeight();
        }
        return weight;
    }

    public boolean compare(Box<?> another) {
        return another.getWeight() == this.getWeight();
    }

    public void infoCompare(Box<?> another) {
        if (compare(another))
            System.out.printf("Вес %.1f=%.1f равен!\n",this.getWeight(),another.getWeight());
        else
            System.out.printf("Вес %.1f≠%.1f не равен!\n",this.getWeight(),another.getWeight());

    }

    public void pourInBox(Box<T> another) {
        System.out.println("Пересыпаем из ящика " + infoBox(this) + " в ящик " + infoBox(another));
        another.fruits.addAll(this.fruits);
        this.fruits.clear();
    }

    public String infoBox(Box box){
        String str = box.fruits.get(0).getClass().toString();
        str = str.substring(str.lastIndexOf(".")+1);
        return  str + " с весом " + box.getWeight();
    }

    public String infoBox(){
        return  "Ящик с весом " + this.getWeight();
    }


    public void addFruit(T fruit) {
            fruits.add(fruit);
    }
}
