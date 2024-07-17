import java.util.Scanner;
/**
 * Class for defining admin data structure and functions
 * 
 * @author Lalit Mohan Joshi
 * @version 0.0.1 || 15/07/2024
 */
public class Admins {
    String name, email;
    private String password;
    /**
     * Making new Admin
     * 
     * @param name
     * @param email
     * @param password
     */
    private Admins(String name, String email, String password){
        this.name = name;
        this.password = password;
        this.email = email;
    }
    /**
     * reading csvline for adding fields in admin object
     * 
     * @param csvLine
     * @return
     */
    public static Admins fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        // (String fileName, String name, String publication, String authors, String genre , Double price)
        return new Admins(
            fields[0],
            fields[1],
            fields[2]
        );
    }
    /**
     * checking email for authentication
     * 
     * @param email
     * @return true if 
     * 
     */
    public boolean validateEmail(String email){
        System.out.println(this.email);
        return this.email.equals(email);
    }
    /**
     * checking password for authentication
     * 
     * @param password
     * @return true if password is rigth
     */
    public boolean validatePassword(String password){
        return this.password.equals(password);
    }
    /**
     * To set new password for the admin
     * 
     * @return true if successfully password is changed
     */
    public boolean setPassword(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your old password");
        String oldPassword = sc.nextLine();
        if (this.validatePassword(oldPassword)) {
            boolean x = true;
            do{
                System.out.println("Enter new password: ");
                String newPassword = sc.nextLine();
                if(newPassword.contains(" ")){
                    System.out.println("Password must not contain empty space\nWant to retry? \n 1. yes  2.no \n");
                    if(sc.next().equals("2"))return false;
                }
                this.password = newPassword;
                x = false;
            }while(x);
            return true;
        }
        System.out.println("invalid password");
        return false;
    }

    /**
     * To convert admin details to a csv string lines
     * 
     * @return csv string lines.
     */
    @Override
    public String toString(){
        return this.name+","+this.email+","+this.password;
    }
}
