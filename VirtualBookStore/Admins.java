import java.util.Scanner;

public class Admins {
    String name, email;
    private String password;
    private Admins(String name, String email, String password){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public static Admins fromCsvLine(String csvLine) {
        String[] fields = csvLine.split(",");
        // (String fileName, String name, String publication, String authors, String genre , Double price)
        return new Admins(
            fields[0],
            fields[1],
            fields[2]
        );
    }

    public boolean validateEmail(String email){
        System.out.println(this.email);
        return this.email.equals(email);
    }

    public boolean validatePassword(String password){
        return this.password.equals(password);
    }
    
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

    @Override
    public String toString(){
        return this.name+","+this.email+","+this.password;
    }
}
