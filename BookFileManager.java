
import java.io.*;
import java.util.*;
/**
 * Class to maitain functions for managing the books
 * 
 * @author Ayush Goyal 
 * @version 0.0.1 || 9/07/2024
 */
public class BookFileManager {
    private static final String BOOKS_FILE = "books.csv";
    static Scanner sc = new Scanner(System.in);
    List<Book> books;
    /**
     * To load books to the list.
     */
    BookFileManager(){
        books = loadBooks();
    }
    /**
     * To test all the fuctions running properly
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        BookFileManager bkmg = new BookFileManager();
        boolean b = true;
        while (b) {
            bkmg.newBook();
            System.out.println("Enter the author name: ");
            String temp = sc.nextLine();
            bkmg.showBooks(bkmg.filterByAuthor(temp));
            System.out.println("Enter the publication name: ");
            temp = sc.nextLine();
            bkmg.showBooks(bkmg.filterByPublication(temp));
            System.out.println("Enter the book's name: ");
            temp = sc.nextLine();
            bkmg.showBooks(bkmg.filterByName(temp));
            System.out.println("Enter IDs: ");
            temp = sc.nextLine();
            List<String> temps = new ArrayList<>();
            for(String t : temp.split(","))
                temps.add(t);
            bkmg.showBooks(bkmg.filterByIDs(temps));
            bkmg.showAllBooks();
            System.out.println("Choose a book to open:");
            int idx = sc.nextInt() -1 ;
            bkmg.books.get(idx).openBook();
            b = false;
        }
        
    }
    /**
     * To add new book in the list
     * 
     * @return 
     */
    public boolean newBook(){
        System.out.println("Enter the name of the file: ");
        String fileName = sc.nextLine();
        System.out.println("Enter the name of the book: ");
        String name = sc.nextLine();
        System.out.println("Enter the name of the book's publication: ");
        String publication = sc.nextLine();
        System.out.println("Enter the names of the book's authors seperated by commas: ");
        String [] author = sc.nextLine().split(",");
        System.out.println("Enter the names of the book's genre seperated by commas: ");
        String [] genre = sc.nextLine().split(",");
        System.out.print("Enter the price of the book: ");
        Double price = sc.nextDouble();
        String authors = "";
        boolean b = true;
        for(String temp : author){
            if (b) {
                authors += temp;
                b = false; 
            }
            else
                authors += "<>"+temp;
        }
        String genres = "";
        b = true;
        for(String temp : genre){
            if (b) {
                genres += temp;
                b = false; 
            }
            else
                genres += "<>"+temp;
        }
        books.add(new Book(fileName, name, publication, authors, genres , price));
        saveBooks();
        return true;
    }
    /**
     * function to display list of books
     * 
     * @param books
     */
    public void showBooks(List <Integer> indexes){
        
        for(int i = 0; i < indexes.size() ; i++){
            Book book = books.get(indexes.get(i));
            System.out.println("\n-------------------------------------------------------------------------------------");
            System.out.println((i+1)+".) Name of the book: " + book.name );
            System.out.println("Publication house: " + book.publication );
            System.out.print("Authors: " );
            for(String author : book.authors.split("<>")){
                System.out.print(author+",");
            }
            System.out.print("\nPrice: ");
            if(book.price == 0){
                System.out.println("free");
            } else 
                System.out.println(book.price);
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }
    public void showAllBooks(){
        int i = 1;
        for(Book book : books){
            System.out.println("\n-------------------------------------------------------------------------------------");
            System.out.println((i++)+".) Name of the book: " + book.name );
            System.out.println("Publication house: " + book.publication );
            System.out.print("Authors: " );
            for(String author : book.authors.split("<>")){
                System.out.print(author+",");
            }
            System.out.print("\nPrice: ");
            if(book.price == 0){
                System.out.println("free");
            } else 
                System.out.println(book.price);
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }
    /**
     * Saves list of books to the csv file
     * 
     */
    public void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.println(book.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    /**
     * To fetch list of books from the csv file
     * 
     * @return list of books
     */
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(Book.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return books;
    }
    /**
     * To filter out the books by name.
     * 
     * @param name
     * @return Filtered list of books
     */
    public List<Integer> filterByName(String name){
        List<Integer> filteredList = new ArrayList<>();
        int i = 0;
        for(Book book : books){
            if(book.checkName(name)){
                filteredList.add(i);
            }
            i++;
        }
        return filteredList;
    }
    /**
     * To filter out the books by author
     * 
     * @param author
     * @return Filtered list of books
     */
    public List<Integer> filterByAuthor(String author){
        List<Integer> filteredList = new ArrayList<>();
        int i = 0;
        for(Book book : books){
            if(book.checkAuthor(author)){
                filteredList.add(i);
            }
            i++;
        }
        return filteredList;
    }
    /**
     * To filter out the books by genre
     * 
     * @param genre
     * @return Filtered list of books
     */
    public List<Integer> filterByGenre(String genre){
        List<Integer> filteredList = new ArrayList<>();
        int i = 0;
        for(Book book : books){
            if(book.checkName(genre)){
                filteredList.add(i);
            }
            i++;
        }
        return filteredList;
    }
    /**
     * To filter out the books by Publication
     * 
     * @param publication
     * @return Filtered list of books
     */
    public List<Integer> filterByPublication(String publication){
        List<Integer> filteredList = new ArrayList<>();
        int i = 0;
        for(Book book : books){
            if(book.checkPublication(publication)){
                filteredList.add(i);
            }
            i++;
        }
        return filteredList;
    }
    /**
     * To filter book for user's read List 
     * 
     * @param ID
     * @return List of books
     */
    public List<Integer> filterByIDs(List<String> ID){
        List<Integer> filteredList = new ArrayList<>();
        int i = 0;
        for( Book book: books){
            for(String id : ID){
                if(book.checkFile(id)){
                    filteredList.add(i);
                }
            }
            i++;
        }
        return filteredList;
    }
}

