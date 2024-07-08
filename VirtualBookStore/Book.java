
/**
 * Class to store meta data of a book to implement functions on books.
 * 
 * @author Ayush Goyal
 * @version 0.0.1 || 8/7/2024
 */
public class Book {
    String name , authors[] , publication , genre[];
    int id;
    Book(String name , String authors[] , String publication , String genre[], int id){
        this.name = name;
        this.authors = authors;
        this.publication = publication;
        this.genre = genre;
        this.id = id;
    }
}
