import java.io.*;
import java.util.*;
/**
 * Write a description of gptUser here.
 * 
 * @author Copilot
 * @version 0.0.1 || 9/07/2024
 */

public class gptUserFileManager {
    private static final String USERS_FILE = "users.csv";
    public static void main(String[] args) {
        // Example usage:
        List<gptUser> users = new ArrayList<>();
        users.add(new gptUser("John Doe", "johndoe@email.com", "hashed_password", 100.0));

        // Save users to CSV file
        savegptUsers(users);
    }

    public static void savegptUsers(List<gptUser> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.csv"))) {
            for (gptUser user : users) {
                writer.println(user.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<gptUser> loadgptUsers() {
        List<gptUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(gptUser.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
}

