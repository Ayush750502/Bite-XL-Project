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
    /**
     * To run initial functions
     */
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
    /**
     * To verify that the user has succesfully logged in or not
     * 
     * @return true when user logges in.
     */
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
            }
        }
        while (ch != '0');
        return false;
    }
    /**
     * To authenticate user
     * 
     * @return true if user in true else false
     */
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
    /**
     * To create new user
     */
    void createUser(){
        System.out.print("\nEnter your firstname: ");
        String fn = sc.next();
        System.out.print("\nEnter your lastname: ");
        String ln = sc.next();
        System.out.print("\nEnter your email: ");
        String email = sc.next();
        System.out.print("\nEnter new password: ");
        String password = sc.next();
        if(userManager.newUser((fn+" "+ln), email, password)){
            System.out.println("New user is created!");
        } else {
            System.out.println("Failed to create user!\nPlease try again later!");
        }
    }
    /**
     * To give search options for books
     */
    void searchBooks(){
        List<Integer> bks = new ArrayList<>();
        System.out.println("\nSearch options: ");
        System.out.println("0. Exit to main menu:- ");
        System.out.println("a. by name:- ");
        System.out.println("b. by author:- ");
        System.out.println("c. by publication:- ");
        System.out.println("Make your choice: ");
        char ch = sc.next().charAt(0);
        switch (ch) {
            case '0':
                return;
            case 'a':
                System.out.print("\nEnter the name of the book: ");
                String name = sc.next();
                bks = bookManager.filterByName(name);
                break;
            case 'b':
                System.out.print("\nEnter the name of the author: ");
                String author = sc.next();
                bks = bookManager.filterByAuthor(author);
                break;
            case 'c':
                System.out.print("\nEnter the name of the publication: ");
                String publication = sc.next();
                bks = bookManager.filterByPublication(publication);
                break;
            default:
                System.out.println("Invalid option:-");
                break;
            }
        int bk = bookOptions(bks);
        if(bk == -1){
            return;
        }
        do{
            System.out.println("0. Back to main menu:");
            System.out.println("a. Buy:");
            ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    return;
                case 'a':
                double price = bookManager.books.get(bk).price;
                    if(userManager.users.get(userManager.idx).checkWallet(price)){
                        userManager.addBookToList(bookManager.books.get(bk).fileName , price);
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
    /**
     * To show books of the indexs in the list
     * 
     * @param bks
     * @return The index of the book in the list which the user has choosen
     */
    int bookOptions(List<Integer> bks){
        bookManager.showBooks(bks);
        System.out.println("\nEnter your choice: ");
        int ch = sc.nextInt();
        if(ch <= 0){
            return -1;
        }
        return bks.get(ch-1);
    }
    /**
     * To display and perform opreations over the user's list
     * 
     * @throws IOException
     */
    void userBookList() throws IOException{
        List<Integer> bks = bookManager.filterByIDs(userManager.users.get(userManager.idx).books);
        if(bks.size() == 0){
            System.out.println("Their is not book saved in your list.");
            return;
        }
        int bk = bookOptions(bks);
        if(bk == -1)
            return;
        Book book = bookManager.books.get(bk);
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
    /**
     * To show user's profile and perform updation over it
     */
    void userProfile(){
        User ur = userManager.users.get(userManager.idx);
        do{

            System.out.println("\nName: "+ur.name);
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
                    System.out.print("Enter new name:");
                    String newName = sc.next();
                    if(lastValidation()){
                        userManager.users.get(userManager.idx).name = newName;
                    }
                    return;
                case 'b':
                    System.out.print("Enter new email ID:");
                    String newEmail = sc.next();
                    if(lastValidation()){
                        userManager.users.get(userManager.idx).name = newEmail;
                    }
                    return;
                case 'c':
                    System.out.print("Enter old password: ");
                    String xxx = sc.next();
                    if(ur.validatePassword(xxx)){
                        System.out.print("\nEnter new password:");
                        String newPassword = sc.next();
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
    void mainMenu() throws IOException{
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
                    searchBooks();
                    break;
                case 'b':
                    userBookList();
                    break;
                case 'c':
                    userProfile();
                    break;
                default:
                    break;
            }
        } while(true);
    } 

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.open();
        if(app.userLoginStory()){
            app.mainMenu();
        }
        app.bookManager.saveBooks();
    }
}
