import java.util.ArrayList;
import java.util.Scanner;

public class MainReader {
    private static final String PATH = "src/main/java/lesson3/src/main/java/book.txt";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите номер страницы:");
        String page = sc.nextLine();
        long startTime = System.currentTimeMillis()/100;
        Book book = new Book(PATH);
        ArrayList<String> pages = book.getPages();
        System.out.println(pages.get(Integer.parseInt(page)-1));
        System.out.println("Время выполнения:" + (System.currentTimeMillis()/100-startTime));
    }
}
