import java.util.ArrayList;
import java.util.List;

/**
 * Class to store meta data and some basic functions of a user to implement functions on user.
 * 
 * @author Ayush Goyal
 * @version 0.0.1 || 9/07/2024
 */
public class User {
    String name;
    String email;
    List<String> books;
    private String password;
    double wallet;
    /**
     * framing user details .
     * 
     * @param name
     * @param email
     * @param books
     * @param password
     * @param wallet
     */
    User( String name , String email , List<String> books, String password , double wallet){
        this.name = name ;
        this.email = email;
        this.books = books;
        this.password = password;
        this.wallet = wallet;
    }
    
    /**
     * To check password.
     * 
     * @param password
     * @return true if the password is matched else false
     */
    public boolean validatePassword(String password ){
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }
    /**
     * To check email.
     * 
     * @param email
     * @return true if the email is matched else false
     */
    public boolean validateEmail(String email){
        if(this.email.equals(email)){
            return true;
        }
        return false;
    }
    /**
     * checks for the amount in the wallet to be suffcient
     * 
     * @param amt 
     * @return true if the amount is less or equal to that of the wallet else false
     */
    public boolean checkWallet(Double amt){
        if(this.wallet < amt){
            return false;
        }
        return true;
    }
    public void newPassword(String newPass){
        this.password = newPass;
    }
    public double addMoney(Double amt){
        this.wallet += amt ;
        return wallet;
    }
    public void showUser(List<Book> books){
        int i = 1;
        System.out.println( "Users's name: "+name + ",\nUsers's email: " + email + ",\nUser's Book List:-");
        for(Book book : books){
            System.out.println(i+". "+book.name);
        }
        System.out.println(",Balence: " + wallet);
    }
    /**
     * Converting csv line into User
     *
     * @param csvLine a line from csv file
     * @return user from the csv file
     */
    public static User fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        List<String> books = new ArrayList<>();
        for(String book : fields[2].split("<>")){
            books.add(book);
        }
        return new User(fields[0], fields[1], books, fields[3], Double.parseDouble(fields[4]));
    }
    /**
     * To produce csv lines from user.
     * 
     * @return comma seperated varables string
     */
    public String toCsvLine() {
        String csvBk = "";
        int bkCount = books.size();
        if(bkCount>0){
            csvBk = books.get(0);
            for(int i = 1 ; i < bkCount ; i++){
                csvBk += "<>" + books.get(i);
            }
        }
        return name + "," + email + ","+ csvBk + "," + password + "," + wallet;
    }
    
}

