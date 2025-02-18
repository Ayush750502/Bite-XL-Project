
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
    public static void main(String[] args){
        System.out.println("this is bookfile class");
    }
    /*
     * function that imports book's pdf to books folder
     */
    private String importPDF() {
        // Open file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a PDF file");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));
        System.out.println("Choose the file path");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Define the destination folder
            String destinationFolder = "./Books"; // Replace with your destination folder path
            File destinationDir = new File(destinationFolder);
            
            // Create destination folder if it doesn't exist
            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }
            String fileName = selectedFile.getName();
            fileName = fileName.substring(0 , selectedFile.getName().indexOf(".pdf"));
            System.out.println("selected file: "+fileName+"\n");
            // Define the destination file path
            File destinationFile = new File(destinationDir, selectedFile.getName());
            
            try {
                // Copy the file to the destination folder
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "File successfully imported!");
                return fileName;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred while copying the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
    /**
     * To add new book in the list
     * 
     * @return 
     */
    public boolean newBook(){
        System.out.println("Enter any key and hit enter to start adding book");
        sc.nextLine();
        System.out.println("Loading......!");
        String fileName = importPDF();
        if(fileName.length()<=0){
            UtilityFuntions.clearScreen();
            fileName = "";
            System.out.println("no file recieved ");
            return false;
        }
        System.out.println("Enter the name of the book: ");
        String name = sc.nextLine();
        if(searchByName(name) != null){
            System.out.println("Book with name "+name+ " already exists.");
            return false;
        }
        System.out.println("Enter the name of the book's publication: ");
        String publication = sc.nextLine();
        System.out.println("Enter the names of the book's authors seperated by commas: ");
        String [] author = sc.nextLine().split(",");
        System.out.println("Enter the names of the book's genre seperated by commas: ");
        String [] genre = sc.nextLine().split(",");
        System.out.print("Enter the price of the book: ");
        Double price = Double.valueOf(sc.next());
        String authors = "";
        boolean b = true;
        for(String temp : author){
            if (b) {
                authors += temp.trim();
                b = false; 
            }
            else
                authors += "<>"+temp.trim();
        }
        String genres = "";
        b = true;
        for(String temp : genre){
            if (b) {
                genres += temp.trim();
                b = false; 
            }
            else
                genres += "<>"+temp.trim();
        }
        books.add(new Book(fileName, name, publication, authors, genres , price));
        saveBooks();
        System.out.println("Book added to catalogue");
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
                System.out.print(author+", ");
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
                System.out.print(author+", ");
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
            if(book.checkGenre(genre)){
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

    public void removeBook(){
        this.showAllBooks();
        System.out.print("enter the name of the book to remove: ");
        String bookName = sc.nextLine();
        Book book  = searchByName(bookName);
        if(book == null){
            System.out.println("No book with name "+bookName+" exists.");
            return;
        }
        books.remove(book);
        saveBooks();
        System.out.println("Book successfully removed");
    }
    private Book searchByName(String bookName) {
        for(Book book : books){
            if(bookName.equalsIgnoreCase(book.name)){
                return book;
            }
        }
        return null;
    }
}

