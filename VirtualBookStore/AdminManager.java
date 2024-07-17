import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * To manage admins in and their opreations
 * 
 * @author Lalit Mohan Joshi
 * @version 0.0.1 || 15/07/2024
 */
public class AdminManager {
    private static Scanner sc = new Scanner(System.in);
    private static String ADMIN_FILE = "admin.csv";
    Admins currAdmin = null;
    List<Admins> admins;
    /**
     * To initialize admin list
     */
    public AdminManager(){
        admins = init();
    }
    /**
     * Function to initate admin list from csv file.
     * 
     * @return List of registered admins
     */
    public List<Admins> init() {
        List<Admins> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                admins.add(Admins.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        this.admins = admins;
        return admins;
    }
    /**
     * To initiate authentication for the admin
     * 
     * @param email
     * @param password
     * @return true if the admin is accurate
     */
    public boolean login(String email , String password){
        System.out.println(email+" "+ password);
        for(Admins admin : admins){
            if(admin.validateEmail(email)){
                if(admin.validatePassword(password)){
                    this.currAdmin = admin;
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * To remove / terminate user from the application
     * 
     * @param um
     */
    public void removeUser(UserFileManager um){
        System.out.println("Existing users:");
        um.showAllUsers();
        List<User> users = um.users;
        System.out.println("Enter email of userto remove!(case sensitive)");
        String email = sc.next();
        for (User user : users) {
            if(user.validateEmail(email)){
                users.remove(user);
                UserFileManager.saveUsers(users);
                System.out.println("Removed user!");
                return;
            }
        }
        System.out.println("User with email: "+email+" doesn't exists, do you mean other email");
    }
    /**
     * To init newBook in the book list
     * 
     * @param bk
     */
    public void addBook(BookFileManager bk){
        bk.newBook();
    }
    /**
     * To remove any book from the book list
     * 
     * @param bk
     */
    public void removeBook(BookFileManager bk){
        bk.removeBook();
    }
    /**
     * Function to update admin password
     */
    public void updatePassword() {
        if(currAdmin.setPassword()){
            System.out.println("Updated password successfully");
            saveAdmins();
            return;
        }
        System.out.println("Password update unsuccessful");
    }
    /**
     * to logout user.
     */
    public void logout(){
        currAdmin = null;
    }
    /**
     * To save admin changes to csv file.
     */
    private void saveAdmins() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ADMIN_FILE))) {
            for (Admins user : admins) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
