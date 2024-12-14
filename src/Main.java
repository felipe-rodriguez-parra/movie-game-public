import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nickname: ");
        String nickname = scanner.nextLine().trim();
        GuessTheMovie.startGame(nickname);
        scanner.close();
    }
}
