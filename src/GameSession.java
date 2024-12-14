import java.util.*;

class GameSession {
    private final String movie;
    private final int maxAttempts;
    private int attempts;
    private int score;
    private final String nickname;
    private final Set<Character> guessedLetters;
    private final Set<Character> incorrectLetters;
    private final Scanner scanner;

    public GameSession(String movie, int maxAttempts, String nickname) {
        this.movie = movie;
        this.maxAttempts = maxAttempts;
        this.attempts = maxAttempts;
        this.score = 0;
        this.nickname = nickname;
        this.guessedLetters = new HashSet<>();
        this.incorrectLetters = new HashSet<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (attempts > 0) {
            displayGameState();
            System.out.println("Opciones: (1) Adivinar letra, (2) Adivinar título, (3) Salir");
            System.out.print("Elija una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    guessLetter();
                    break;
                case "2":
                    guessTitle();
                    return;
                case "3":
                    System.out.println("Has decidido salir del juego. Fin del juego.");
                    attempts = 0;
                    return;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            if (getMaskedTitle().equalsIgnoreCase(movie)) {
                System.out.println("¡Has adivinado el título completo!");
                return;
            }
        }
    }

    private void displayGameState() {
        System.out.println("\nPelícula: " + getMaskedTitle());
        System.out.println("Intentos restantes: " + attempts);
        System.out.println("Errores: " + incorrectLetters);
        System.out.println("Puntuación: " + score);
    }

    private void guessLetter() {
        System.out.print("Ingrese una letra: ");
        String letterInput = scanner.nextLine().toLowerCase();
        if (letterInput.length() != 1 || !Character.isLetter(letterInput.charAt(0))) {
            System.out.println("Entrada inválida, por favor ingrese una sola letra [a-z].");
            return;
        }
        char letter = letterInput.charAt(0);
        if (guessedLetters.contains(letter) || incorrectLetters.contains(letter)) {
            System.out.println("Ya has adivinado esa letra.");
            return;
        }
        if (movie.toLowerCase().contains(String.valueOf(letter))) {
            System.out.println("¡Letra correcta!");
            guessedLetters.add(letter);
            score += 10;
        } else {
            System.out.println("Letra incorrecta.");
            incorrectLetters.add(letter);
            attempts--;
            score -= 10;
        }
    }

    private void guessTitle() {
        System.out.print("Ingrese el título completo: ");
        String guess = scanner.nextLine().trim();
        if (guess.equalsIgnoreCase(movie)) {
            System.out.println("¡Correcto! Has ganado el juego.");
            score += 20;
            attempts = 0;
        } else {
            System.out.println("Incorrecto. Has perdido el juego.");
            score -= 20;
            attempts = 0;
        }
    }

    private String getMaskedTitle() {
        StringBuilder maskedTitle = new StringBuilder();
        for (char c : movie.toCharArray()) {
            if (c == ' ' || c == '-') {
                maskedTitle.append(c);
            } else if (guessedLetters.contains(Character.toLowerCase(c))) {
                maskedTitle.append(c);
            } else {
                maskedTitle.append('*');
            }
        }
        return maskedTitle.toString();
    }

    public String getMovie() {
        return movie;
    }

    public int getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }
}
