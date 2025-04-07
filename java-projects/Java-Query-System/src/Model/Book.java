package Model;

import Util.QueryItems;

import java.util.Comparator;
import java.util.Random;

public class Book implements QueryItems, Comparable<Book>{

    private String title;
    private String author;
    private int yearPublished;
    private int bookId;

    protected static Random random = new Random();
    private static String[] titles = {"Life of Pi", "Narnia","Lord lost", "Hamlet", "The Tempest"};
    private static String[] authors = {"Shakespear", "Pratav", "John Wick","Aslan"};

    public Book(){
        this.title = titles[random.nextInt(5)];
        this.author = authors[random.nextInt(4)];
        this.yearPublished = random.nextInt(2010,2024);
        this.bookId = random.nextInt(1000,8000);
    }

    public int getYearPublished(){
        return yearPublished;
    }

    @Override
    public boolean matchFieldValue(String field, String value) {

        return switch(field.toLowerCase()){
            case "title" -> title.equalsIgnoreCase(value);
            case "author" -> author.equalsIgnoreCase(value);
            case "year" -> yearPublished == Integer.parseInt(value);
            default -> false;
        };
    }

    @Override
    public int compareTo(Book o) {
        return Integer.compare(this.bookId,o.bookId);
    }

    Comparator<Book> comparator = Comparator.comparing(Book::getYearPublished);

    @Override
    public String toString() {
        return "ID: %-15d Title: %-15s Author: %-15s Year Published: %-15d".formatted(bookId,title,author,yearPublished);
    }
}
