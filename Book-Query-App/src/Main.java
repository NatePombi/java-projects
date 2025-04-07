import Model.Book;
import Model.Ebook;
import Util.QueryList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int num = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(new Book());
        }
        QueryList<Book> bk = new QueryList<>(books);
        List<Book> filter = bk.getMatches("author", "Shakespear");

        System.out.println("Books:");
        filter.sort(Comparator.comparing(Book::getYearPublished));
        filter.forEach(System.out::println);


        System.out.println("---".repeat(30));


        List<Ebook> ebooks = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ebooks.add(new Ebook());
        }

        QueryList<Ebook> ebk = new QueryList<>(ebooks);
        List<Ebook> filler = ebk.getMatches("file", "5");

        System.out.println("Ebooks:");
        filler.sort(Comparator.comparing(Ebook::getFileSizeMB));
        filler.forEach(System.out::println);
    }


    public static void printList(List<? extends Book> item) {

        for (var s : item) {
            System.out.println(s);

        }

    }
}
