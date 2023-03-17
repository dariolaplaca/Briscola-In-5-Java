import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game(Arrays.asList("Dario", "Alessio", "Ivan"));
        boolean isRunning = true;
        while(isRunning){
            game.runGame();
            isRunning = false;
        }
    }
}