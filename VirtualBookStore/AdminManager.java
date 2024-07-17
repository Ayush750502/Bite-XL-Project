import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * 
 */
public class AdminManager {
    private static Scanner sc = new Scanner(System.in);
    private static String ADMIN_FILE = "admin.csv";
    Admins currAdmin = null;
    List<Admins> admins;
    public AdminManager(){
        admins = init();
    }
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

    public void addBook(BookFileManager bk){
        bk.newBook();
    }

    public void removeBook(BookFileManager bk){
        bk.removeBook();
    }

    public void updatePassword() {
        if(currAdmin.setPassword()){
            System.out.println("Updated password successfully");
            saveAdmins();
            return;
        }
        System.out.println("Password update unsuccessful");
    }

    public void logout(){
        currAdmin = null;
    }

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
