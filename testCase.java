import java.io.*;
import java.awt.Desktop;

/**
 * Write a description of testCase here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class testCase {
    public static void main() throws IOException{
        File file = new File("Books/0001.pdf");
        if (file.exists() && file.isFile()) {
            System.out.println("File found. Opening...");
            // Open the file here, e.g., using Desktop class
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } else {
            System.out.println("File not found.");
        }

        }
}
