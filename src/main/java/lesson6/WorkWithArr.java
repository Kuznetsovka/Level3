package lesson6;

import java.util.Arrays;

public class WorkWithArr {

    public int[] modifyArr(int[] arr) {
        if (arr[arr.length-1] == 4) return null;
        for (int i = arr.length-1; i >=0; i--) {
            if (arr[i] == 4)
                return Arrays.copyOfRange(arr, i+1, arr.length);
        }
        throw new RuntimeException();
    }

    public boolean isOneFour(int[] arr){
        int one = 0;
        int four = 0;
        for (int value : arr) {
            if (value == 1)
                one++;
            else if (value == 4) {
                four++;
            } else {
                throw new RuntimeException();
            }
        }
        return (one==0 || four==0);
    }

}
/*
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки.
Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы, то метод вернет false;
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */
