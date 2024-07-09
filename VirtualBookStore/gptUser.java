import java.io.*;
import java.util.*;
/**
 * Write a description of gptUser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gptUser {
    private String name;
    private String email;
    private String password;
    private double wallet;
    gptUser( String name , String email , String password , double wallet){
        this.name = name ;
        this.email = email;
        this.password = password;
        this.wallet = wallet;
    }
    // Constructors, getters, setters, toString, equals, hashCode methods
    // ...

    /**
     * Method fromCsvLine
     *
     * @param csvLine A parameter
     * @return The return value
     */
    public static gptUser fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        return new gptUser(fields[0], fields[1], fields[2], Double.parseDouble(fields[3]));
    }

    public String toCsvLine() {
        return name + "," + email + "," + password + "," + wallet;
    }
}

