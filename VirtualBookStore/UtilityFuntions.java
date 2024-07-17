public class UtilityFuntions {

    static void clearScreen() {
        // ANSI escape code to clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args){
        System.out.println("this is utility class");
    }
}
