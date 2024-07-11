import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
    void open(){
        BookFileManager.loadBooks();
        userManager.loadUsers();
    }
    boolean lastValidation(){
        System.out.println("Are you sure? (y/n)");
        char ch = sc.next().charAt(0);
        switch (ch) {
            case 'y':
                return true;
            default:
                return false;
        }
    }
    boolean userLoginStory(){
        char ch;
        do{
            System.out.println("a. Login");
            System.out.println("b. Sign up");
            System.out.println("0. Exit");
            ch = sc.next().charAt(0);
            switch (ch) {
                case 'a':
                return userLogin();
                case 'b':
                    createUser();
                    break;
                default:
                    System.out.println("Invalide Input");
                    break;
            }
            return false;
        }
        while (ch != '0');
    }
    boolean userLogin(){
        System.out.println("\nEnter the email: ");
        String email = sc.next();
        System.out.println("Enter the password: ");
        String password = sc.next();
        boolean b = userManager.login(email, password);
        if(b){
            System.out.println("\nLogin successfull");
        } else {
            System.out.println("\nEither your email or your password is wrong!");
        }
        return b;
    }
    void createUser(){
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.next();
        System.out.println("Enter new password");
        String password = sc.next();
        if(userManager.newUser(name, email, password)){
            System.out.println("New user is created!");
            userLogin();
        } else {
            System.out.println("Failed to create user!\nPlease try again later!");
        }
    }
    void searchBooks(){
        List<Integer> bks = new ArrayList<>();
        System.out.println("\nSearch options: ");
        System.out.println("0. Exit to main menu:- ");
        System.out.println("a. by name:- ");
        System.out.println("b. by author:- ");
        System.out.println("c. by publication:- ");
        System.out.println("Make your choice: ");
        char ch = sc.nextLine().charAt(0);
        switch (ch) {
            case '0':
                return;
            case 'a':
                System.out.println("Enter the name of the book: ");
                String name = sc.nextLine();
                bks = bookManager.filterByName(name);
                break;
            case 'b':
                System.out.println("Enter the name of the author: ");
                String author = sc.nextLine();
                bks = bookManager.filterByAuthor(author);
                break;
            case 'c':
                System.out.println("Enter the name of the publication: ");
                String publication = sc.nextLine();
                bks = bookManager.filterByPublication(publication);
                break;
            default:
                System.out.println("Invalid option:-");
                break;
            }
        int bk = bookOptions(bks);
        do{
            System.out.println("0. Back to main menu:");
            System.out.println("a. Buy:");
            ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    return;
                case 'a':
                    if(userManager.users.get(userManager.idx).checkWallet(BookFileManager.books.get(bk).price)){
                        userManager.addBookToList(BookFileManager.books.get(bk).fileName);
                        System.out.println("Book added successfully!\n");
                    } else 
                        System.out.println("You don't have suffecient amount in your wallet!\n");
                    return;
                default:
                    System.out.println("\nInvalid option!\n");
                    break;
            }
        } while(true);

    }
    int bookOptions(List<Integer> bks){
        bookManager.showBooks(bks);
        System.out.println("\nEnter your choice: ");
        int ch = sc.nextInt();
        return bks.get(ch);
    }
    void userBookList() throws IOException{
        List<Integer> bks = bookManager.filterByIDs(userManager.users.get(userManager.idx).books);
        int bk = bookOptions(bks);
        Book book = BookFileManager.books.get(bks.get(bk));
        do{

            System.out.println("\n0. Back to Main Menu:");
            System.out.println("a. Open Book:");
            // System.out.println("b. Remove book from List:");
            char ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    return;
                case 'a':
                    book.openBook();
                    return;
                // case 'b':
                    // userManager.removeBookFromList(book.fileName);
                    // return;
                default:
                    System.out.println("Invalid Option!");
                    break;
                }
        } while(true);
    }
    void userProfile(){
        User ur = userManager.users.get(userManager.idx);
        do{

            System.out.println("Name: "+ur.name);
            System.out.println("Email: "+ur.email);
            System.out.println("Wallet Balance: "+ur.wallet);
            System.out.println("\n\n0. Back To main Menu:");
            System.out.println("a. Update your name:");
            System.out.println("b. Update your email:");
            System.out.println("c. Update password:");
            System.out.println("d. Add money:");
            char ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    return;
                case 'a':
                    System.out.println("Enter new name:");
                    String newName = sc.nextLine();
                    if(lastValidation()){
                        userManager.users.get(userManager.idx).name = newName;
                    }
                    return;
                case 'b':
                    System.out.println("Enter new email ID:");
                    String newEmail = sc.nextLine();
                    if(lastValidation()){
                        userManager.users.get(userManager.idx).name = newEmail;
                    }
                    return;
                case 'c':
                    System.out.println("Enter old password: ");
                    String xxx = sc.nextLine();
                    if(ur.validatePassword(xxx)){
                        System.out.println("Enter new password:");
                        String newPassword = sc.nextLine();
                        if(lastValidation()){
                            userManager.users.get(userManager.idx).newPassword(newPassword);
                        }
                    } else 
                        System.out.println("Password is wrong!");
                    return;
                case 'd':
                    System.out.println("Enter the amount to add in your wallet:");
                    Double n = sc.nextDouble();
                    if(n > 0){
                        userManager.users.get(userManager.idx).addMoney(n);
                    } else 
                        System.out.println("Invalid amount");
                    return;
                default:
                    System.out.println("Invalid Choice!\n");
                    break;
            }
        } while(true);
    }
    void mainMenu(){
        do{
            System.out.println("\nMain Menu:");
            System.out.println("0. To exit:- ");
            System.out.println("a. Search Book:- ");
            System.out.println("b. View your book list:- ");
            System.out.println("c. View your profile:- ");
            System.out.println("Make your choice: ");
            char ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    System.out.println("Good Bye!");
                    return;
                case 'a':
    
                default:
                    break;
            }
        } while(true);
    } 

    public static void main(String[] args) {
        App app = new App();
        app.open();
        if(app.userLoginStory()){
            app.mainMenu();
        }
        BookFileManager.saveBooks();
    }
}
