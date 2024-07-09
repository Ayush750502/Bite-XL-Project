
import java.io.*;
import java.util.*;
/**
 * Write a description of gptBook here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gptBook {

    private int id;
    String name;
    String publication;
    String authors;
    String content;

    public gptBook(int id, String name, String publication, String authors, String content) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.authors = authors;
        this.content = content;
    }

    // Getters, setters, toString, equals, hashCode methods
    // ...

    public static gptBook fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        return new gptBook(
            Integer.parseInt(fields[4]),
            fields[0],
            fields[1],
            fields[2],
            fields[3]
        );
    }

    public String toCsvLine() {
        return name + "," + publication + "," + authors + "," + content + "," + id;
    }


}
