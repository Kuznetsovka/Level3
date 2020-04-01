package lesson1;

public abstract class Fruit {
    protected float weight;

    public float getWeight() {
        return weight;
    }

    public Fruit() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return Float.compare(fruit.weight, weight) == 0;
    }

}
