package lesson1;

public class Orange extends Fruit {
    protected float weight;

    @Override
    public float getWeight() {
        return weight;
    }

    public Orange() {
        super();
        this.weight = 1.5f;
    }
}
