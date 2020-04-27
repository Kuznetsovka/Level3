package lesson8;

public class Main {
    public static void main(String[] args) {
        try {
            Matrix matrix = new Matrix(7);
            matrix.fillSpiral();
            matrix.printArr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
