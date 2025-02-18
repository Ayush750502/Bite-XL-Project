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
    Scanner sc;
    BookFileManager bookManager;
    UserFileManager userManager;
    AdminManager adminManager;
    private boolean isAdmin;
    private boolean isLoggedin, exit;
    public App(){
        exit = false;
        sc = new Scanner(System.in);
        bookManager = new BookFileManager();
        userManager = new UserFileManager();
        adminManager = new AdminManager();
        isAdmin = false;
        isLoggedin = false;
    }
    /**
     * To run initial functions
    //  */
    // void open(){
    //     BookFileManager.loadBooks();
    //     // userManager.loadUsers();
    //     // adminManager.init();
    // }
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
     *  also to check if person logged int is user or admin
     * @return true when user logges in.
     */
    boolean userLoginStory(){
        char ch;
        do{
            System.out.println("a. Login");
            System.out.println("b. Sign up\nc. Admin login");
            System.out.println("0. Exit");
            ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    exit = true;
                    break;
                case 'a':
                    userManager.loadUsers();
                    return userLogin();
                case 'b':
                    userManager.loadUsers();
                    createUser();
                    break;
                case 'c':
                    adminManager.init();
                    if(adminLogin()){
                        isAdmin = true;
                        return true;
                    }
                    return false;
                default :
                    System.out.println("Invalid inputs!");
            }
            UtilityFuntions.clearScreen();
        }
        while (ch != '0');
        return false;
    }
    /**
     * To run admin login console.
     * 
     * @return true if the admin is valide
     */
    private boolean adminLogin() {
        System.out.println("\nEnter the email: ");
        String email = sc.next();
        System.out.println("Enter the password: ");
        String password = sc.next();
        boolean b = adminManager.login(email, password);
        if(b){
            UtilityFuntions.clearScreen();
            System.out.println("\nLogin successfull");
        } else {
            UtilityFuntions.clearScreen();
            System.out.println("\nEither your email or your password is wrong!");
        }
        return b;
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
            UtilityFuntions.clearScreen();
            System.out.println("\nLogin successfull");
        } else {
            UtilityFuntions.clearScreen();
            System.out.println("\nEither your email or your password is wrong!");
        }
        return b;
    }
    /**
     * To create new user
     */
    void createUser(){
        System.out.print("\nEnter your firstname: ");
        String fn = sc.next().trim();
        System.out.print("\nEnter your lastname: ");
        String ln = sc.next().trim();
        System.out.print("\nEnter your email: ");
        String email = sc.next().trim();
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
        System.out.println("d. by genre:- ");
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
            case 'd':
                System.out.print("\nEnter the genre: ");
                String genre = sc.next();
                bks = bookManager.filterByGenre(genre);
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
        System.out.println("\nEnter your choice: \n0. To go back");
        
        sc.nextLine();
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
        if(bks.isEmpty()){
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
            try{
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
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        } while(true);
    }   
    /**
     * To show admin profile
     */
    void adminProfile(){
        Admins admin = adminManager.currAdmin;
        System.out.println("\nName: "+admin.name);
        System.out.println("Email: "+admin.email);
        System.out.println("\n\n 1. Update password\nEnter anything to go back To main Menu");
        if(sc.next().equals("1")){
            adminManager.updatePassword();
        }
    }
    /**
     * To show user menu options
     * 
     * @throws IOException
     */
    void userMenu() throws IOException{
        char ch;
        do{
            System.out.println("\nMain Menu:");
            System.out.println("0. To exit:- ");
            System.out.println("a. Search Book:- ");
            System.out.println("b. View your book list:- ");
            System.out.println("c. View your profile:- ");
            System.out.println("z. To logout:- ");
            System.out.println("Make your choice: ");
            ch = sc.next().charAt(0);
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
                case 'z':
                    userManager.logout();
                    UtilityFuntions.clearScreen();
                    isLoggedin = false;
                    return;
                default:
                    break;
            }
        } while(ch != '0');
    } 
    /**
     * To show admin menu options
     * @throws IOException
     */
    void adminMenu() throws IOException{
        char ch;
        do{
            System.out.println("\nMain Menu:");
            System.out.println("0. To exit:- ");
            System.out.println("a. Add a book:- ");
            System.out.println("b. Remove a book:- ");
            System.out.println("c. Remove a user:- ");
            System.out.println("d. View your profile:- ");
            System.out.println("e. Show all books available");
            System.out.println("f. Show all users available");
            System.out.println("z. To logout:- ");
            System.out.println("Make your choice: ");
            ch = sc.next().charAt(0);
            switch (ch) {
                case '0':
                    System.out.println("Good Bye!");
                    return;
                case 'a':
                    adminManager.addBook(bookManager);
                    break;
                case 'b':
                    adminManager.removeBook(bookManager);
                    break;
                case 'c':
                    adminManager.removeUser(userManager);
                    break;
                case 'd':
                    adminProfile();
                    break;
                case 'e':
                    bookManager.showAllBooks();
                    break;
                case 'f':
                    userManager.showAllUsers();
                    break;
                case 'z':
                    adminManager.logout();
                    UtilityFuntions.clearScreen();
                    isAdmin = false;
                    isLoggedin = false;
                    return;
                default:
                    break;
            }
        } while(ch != '0');
    } 
    /**
     * To run app through main function.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        App app = new App();
        // app.open();
        do{
            if(app.userLoginStory()){
                app.isLoggedin = true;
                if(!app.isAdmin)
                    app.userMenu();
                else
                    app.adminMenu();
            }
        }while(!app.isLoggedin && !app.exit);
        app.bookManager.saveBooks();
        System.out.println("Happy coding!");
    }

}


