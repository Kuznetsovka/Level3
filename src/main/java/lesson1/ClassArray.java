package lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassArray<T>  {
    private T[] obj;

    public ClassArray(T... obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return ("ClassArray{" +
                "obj=" + Arrays.toString(obj) +
                '}');
    }

    public void replaceIndex(int firstIndex, int secondIndex) {
        T temp = obj[firstIndex];
        obj[firstIndex] = obj[secondIndex];
        obj[secondIndex] = temp;
    }

    public ArrayList<T> convertToArrayList() {
        List list = Arrays.asList(obj);
        ArrayList<T> arrayList = new ArrayList<T>(list);
        return arrayList;
    }
}
