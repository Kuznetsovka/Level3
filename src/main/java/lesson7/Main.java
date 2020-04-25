package lesson7;

public class Main {
    public static void main(String[] args) {
        int COUNT=10;
        Calc[] calc = new Calc[COUNT];
        for (int i = 1; i < COUNT; i++) {
            calc[i] = new Calc(i,i);
            new Testing(calc[i], i, i+1);
        }

    }
}
