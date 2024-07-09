
import java.io.*;
import java.util.*;
/**
 * Write a description of gptBookManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gptBookFileManager {
    private static final String BOOKS_FILE = "books.csv";

    public static void main(String[] args) {
        // Example usage:
        List<gptBook> books = new ArrayList<>();
        books.add(new gptBook(1, "gptBook A", "Publisher X", "Author1 <> Author2", "Sample content"));

        // Save books to CSV file
        savegptBooks(books);
        // Load Book
        books = loadgptBooks();
        for(gptBook book : books){
            System.out.println("Name of the book: " + book.name );
            System.out.println("Publication house: " + book.publication );
            System.out.print("Authors: " );
            for(String author : book.authors.split("<>")){
                System.out.print(author+",");
            }
            System.out.println();
            System.out.println("content: " + book.content);
        }
    }

    public static void savegptBooks(List<gptBook> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (gptBook book : books) {
                writer.println(book.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<gptBook> loadgptBooks() {
        List<gptBook> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(gptBook.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return books;
    }
}

