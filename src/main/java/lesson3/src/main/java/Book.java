import java.io.*;
import java.util.ArrayList;

public class Book {
    private String path;
    private ArrayList<String> pages;
    private BufferedReader reader;
    private int lengthPage = 1800;

    public Book(String path) {
        this.path = path;
        pages = new ArrayList<>();
        readBook();
    }

    public ArrayList<String> getPages() {
        return pages;
    }

    private void readBook () {

        char[] arr = new char[lengthPage];
        try {
            reader = new BufferedReader(new FileReader(new File(path)));
            while ((reader.read(arr)) != -1) {
                pages.add(String.valueOf(arr));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.

Сделать клиен-серверное приложение. Передать по сети сеарилизованный объект.
*/