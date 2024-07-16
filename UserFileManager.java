import java.io.*;
import java.util.*;
/**
 * Write a description of User here.
 * 
 * @author Ayush Goyal
 * @version 0.0.1 || 9/07/2024
 */
public class UserFileManager {
    static Scanner sc = new Scanner(System.in);
    private static final String USERS_FILE = "users.csv";
    /**
     * Main list of users.
     */
    List<User> users;
    /**
     * Currently logged user's idx
     */
    int idx = -1;
    /**
     * To load users to the list.
     */
    UserFileManager(){
        users = loadUsers();
    }
    /**
     * To test all the functions running properly
     * 
     * @param args
     */
    public static void main(String[] args) {
        UserFileManager mg = new UserFileManager();
        boolean b = true;
        while(b){
            System.out.println("Enter your name : ");
            String name = sc.nextLine();
            System.out.println("Enter your email: ");
            String email = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();
            b = mg.newUser(name , email, password);
            if(b){
                System.out.println("Registration completed!");
            } else {
                b = true;
                continue;
            }
            while (b) {
                System.out.println("Enter email:");
                email = sc.nextLine();
                System.out.println("Enter password:");
                password = sc.nextLine();
                b = mg.login(email, password);
                if(b){
                    System.out.println("Login successfull!");
                } else {
                    System.out.println("Login un-successfull!");
                    b = true;
                    continue;
                }
                // System.out.println("Enter the file name!");
                // String[] fnm = sc.nextLine().split(",");
                // for(String f : fnm){
                    // mg.addBookToList(f);
                    // System.out.println(mg.users.get(mg.idx).toCsvLine());
                // }
                // for(String f : fnm){
                    // mg.removeBookFromList(f);
                    // System.out.println(mg.users.get(mg.idx).toCsvLine());
                // }
                while (b) {
                    System.out.println("Enter email:");
                    email = sc.nextLine();
                    b = !mg.remove(email);
                }
            }
            b = false;
        }
    }
    /**
     * To add books to the user's list
     * 
     * @param ID
     */
    public void addBookToList(String ID , Double amount){
        users.get(idx).books.add(ID);
        users.get(idx).wallet -= amount;
        saveUsers(users);

    }
    /**
     * To remove books from the user's list
     * 
     * @param ID
     */
    public void removeBookFromList(String ID){
        int i = 0;
        for(String book : users.get(this.idx).books){
            if(book.equals(ID)){
                users.get(this.idx).books.remove(i);
                return;
            }
            i++;
        }
    }

    /**
     * Adding money to the wallet
     * 
     * @param amt
     */
    public void addMoney(double amt){
        if(idx > -1){
            users.get(idx).addMoney(amt);
            saveUsers(users);
        } else
            System.out.println("User has not logged in yet");
    }
    public void deductMoney(double amt){
        if (users.get(idx).checkWallet(amt)) {
            users.get(idx).wallet -= amt;
            saveUsers(users);
        } else 
            System.out.println("Amount is not suffiecent!");
    }
    /**
     * To save list of users to csv file.
     * 
     * @param List<User> 
     */
    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(user.toCsvLine());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return users;
    }
    /**
     * To authenticate the user
     * 
     * @param email
     * @param password
     * @return true if the user is found or else false.
     */
    public boolean login(String email , String password){
        int idx = 0;
        for(User user : users){
            if(user.validateEmail(email)){
                if(user.validatePassword(password)){
                    this.idx = idx;
                    return true;
                }
            }
            idx++;
        }
        return false;
    }
    /**
     * To remove user from the user list.
     * 
     * @param email
     * @return false if user not found else true if users is removed
     */
    public boolean remove(String email){
        System.out.print("\t\tEnter password : ");
        if(users.get(this.idx).validatePassword(sc.nextLine()) != true){
            System.out.println("\n\t\tPlease try again with correct password");
            return false;
        }
        int i = 0;
        for(User user : users){
            if(user.validateEmail(email)){
                users.remove(i);
                this.idx = -1;
                saveUsers(users);
                return true;
            }
            idx++;
        }
        return false;
    }
    /**
     * To add new user to the users' list
     * 
     * @param name
     * @param email
     * @param password
     * @return returns false if user email ID is already their 
     */
    public boolean newUser(String name, String email, String password ){
        for(User user: users){
            if(user.validateEmail(email)){
                System.out.println("This email already have an account !\nPlease try with another account");
                return false;
            }
        }

        users.add( new User(name, email, new ArrayList<>() , password, 0.0));
        saveUsers(users);
        return true;
    }
    
}

