package lesson8;

import java.util.Arrays;

public class Matrix {
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 7;
    private int size;
    private int[][] arr;

    public Matrix(int size) throws Exception {
        this.size = size;
        if (size<MIN_SIZE || size>MAX_SIZE)
            throw new Exception(String.format("Указанный размер матрицы не допустим! Минимум - %d, максимум - %d",MIN_SIZE,MAX_SIZE));
        arr = new int[size][size];
    }

    public void printArr(){
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    public void fillSpiral(){
        int step=0;
        int edge=0;
        int i=0;
        while (step!=size * size){
            while(edge++!=4){
                for (int j = 0; j < size; j++) {
                    if (arr[i][j]==0)
                        arr[i][j] = ++step;
                }
                arr = rotateArr();
            }
            i++;
            edge=0;
        }
    }

    private int[][] rotateArr() {
        int[][] resultArr = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultArr[size - j - 1][i] = arr[i][j];
            }
        }
        return resultArr;
    }

}
