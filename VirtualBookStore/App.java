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
    void userStory(){
        char ch;
        do{
            System.out.println("a. Login");
            System.out.println("b. Sign up");
            System.out.println("0. Exit");
            ch = sc.next().charAt(0);
            switch (ch) {
                case 'a':
                    userLogin();
                    break;
                case 'b':
                    createUser();
                    break;
                default:
                    System.out.println("Invalide Input");
                    break;
            }
        }
        while (ch != '0');
    }
    void userLogin(){
        System.out.print("Enter the email: ");
        String email = sc.next();
        System.out.print("\nEnter the password: ");
        String password = sc.next();
        if(userManager.login(email, password)){
            System.out.println("\nLogin successfull");
        } else {
            System.out.println("\nEither your email or your password is wrong");
        }
    }
    void createUser(){
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("\nEnter your email: ");
        String email = sc.next();
        System.out.print("\nEnter new password");
        String password = sc.next();
        if(userManager.newUser(name, email, password)){
            System.out.println("New user is created!");
            userLogin();
        } else {
            System.out.println("Failed to create user!\nPlease try again later!");
        }
    }
    void BookStory(){

    }
    void mainMenu(){
        System.out.println("a. Search Book:- ");
    } 
    public static void main(String[] args) {
        
    }
}
