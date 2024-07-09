import java.util.Scanner;

/**
 * flow of the app.
 * 
 * @author Ayush Goyal
 * @version 0.0.1 || 9/07/2024
 */
public class App {
    Scanner sc = new Scanner(System.in);
    BookFileManager bookManager = new BookFileManager();
    UserFileManager userManager = new UserFileManager();
    App(){
        
    }
    void userLogin(){
        System.out.print("Enter the email: ");
        String email = sc.next();
        System.out.print("\nEnter the password: ");
        String password = sc.next();
        if(userManager.login(email, password)){
            System.out.println("Login successfull");
        } else {
            System.out.println("Either your email or your password is wrong");
        }
    }
    void createUser(){
        System.out.print("\tEnter your name: ");
        String name = sc.nextLine();
        System.out.print("\n\tEnter your email: ");
        String email = sc.next();
        System.out.print("\n\tEnter new password");
        String password = sc.next();
        if(userManager.newUser(name, email, password)){
            System.out.println("New user is created!");
            userLogin();
        } else {
            System.out.println("Failed to create user!\nPlease try again later!");
        }
    }
    public static void main(String[] args) {
        
    }
}
