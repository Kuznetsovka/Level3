package lesson1;

public class Apple extends Fruit {
    protected float weight;

    public float getWeight() {
        return weight;
    }

    public Apple() {
        super();
        this.weight = 1.0f;
    }
}
