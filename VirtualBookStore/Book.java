import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
/**
 * Class to store meta data and some basic functions of a book to implement functions on books.
 * 
 * @author Ayush Goyal
 * @version 0.0.1 || 8/7/2024
 */
public class Book {
    
    String fileName;
    String name;
    String publication;
    String authors;
    String genre;
    Double price;
    /**
     * framing book details .
     * 
     * @param fileName
     * @param name
     * @param publication
     * @param authors
     * @param genre
     * @param price
     */
    public Book(String fileName, String name, String publication, String authors, String genre , Double price) {
        this.fileName = fileName;
        this.name = name;
        this.publication = publication;
        this.authors = authors;
        this.genre = genre;
        this.price = price;

    }
    /**
     * To convert fields form the lines of csv file to book for book list
     * 
     * @param csvLine
     * @return new Book
     */
    public static Book fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        // (String fileName, String name, String publication, String authors, String genre , Double price)
        return new Book(
            fields[0],
            fields[1],
            fields[2],
            fields[3],
            fields[4],
            Double.parseDouble(fields[5])
        );
    }
    public boolean checkFile(String fileName){
        return this.fileName.equals(fileName);
    }
    /**
     * To check for the name of the book
     * 
     * @param name
     * @return boolean
     */
    public boolean checkName(String name){
        if(this.name.equals(name))
            return true;
        return false;
    } 
    /**
     * To check for the author of the book
     * 
     * @param author
     * @return boolean
     */
    public boolean checkAuthor(String author){
        if(this.authors.indexOf(author) >=0)
            return true;
        return false;
    } 
    /**
     * To check for the genre of the book
     * 
     * @param genre
     * @return boolean
     */
    public boolean checkGenre(String genre){
        if(this.genre.indexOf(genre) >=0)
            return true;
        return false;
    } 
    /**
     * To check for the publication of the book
     * 
     * @param publication
     * @return boolean
     */
    public boolean checkPublication(String publication){
        if(this.publication.indexOf(publication) >=0)
            return true;
        return false;
    } 
    /**
     * To open the book in windows
     * 
     * @throws IOException
     */
    public void openBook() throws IOException{
        File file = new File("Books/"+fileName+".pdf");
        if (file.exists() && file.isFile()) {
            System.out.println("File found. Opening...");
            // Open the file here, e.g., using Desktop class
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } else {
            System.out.println("File not found.");
        }

    }
    /**
     * To produce csv file lines
     * 
     * @return comma seperated variables string
     */
    public String toCsvLine(){
        // (String fileName, String name, String publication, String authors, String genre , Double price)
        return fileName + "," + name+","+ publication+","+ authors+","+genre+","+ price;
    }

    @Override 
    public String toString(){
        StringBuilder res = new StringBuilder("{ Name: "+name+", Publication: "+publication+", Authors: [");
        for (String s : authors.split("<>")) {
            res.append(s+", ");
        }
        res.append("], Genre: [");
        for (String s : genre.split("<>")) {
            res.append(s+", ");
        }
        res.append("], Price: "+price);
        return res.toString();
    }
}
