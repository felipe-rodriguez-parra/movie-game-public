import java.io.*;
import java.util.*;

public class GuessTheMovie {
    private static final int MAX_ATTEMPTS = 10;
    private static final String MOVIES_FILE = new File(System.getProperty("user.dir") + File.separator + "src", "movies.txt").getPath();
    private static final String SCORES_FILE = new File(System.getProperty("user.dir") + File.separator + "src", "ranking.dat").getPath();
    private static final int MAX_RANKING = 5;

    public static void startGame(String nickname) throws IOException {
        List<String> movies = loadMovies();
        if (movies.isEmpty()) {
            System.out.println("No hay películas disponibles para jugar.");
            return;
        }
        String movieToGuess = movies.get(new Random().nextInt(movies.size()));
        playGame(movieToGuess, nickname);
    }

    private static List<String> loadMovies() throws IOException {
        List<String> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MOVIES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                movies.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de películas no encontrado.");
        }
        return movies;
    }

    private static void playGame(String movie, String nickname) throws IOException {
        GameSession session = new GameSession(movie, MAX_ATTEMPTS, nickname);
        session.start();
        endGame(session);
    }

    private static void endGame(GameSession session) throws IOException {
        System.out.println("\nEl título era: " + session.getMovie());
        System.out.println("Puntuación final: " + session.getScore());
        if (session.getScore() > 0) {
            updateRanking(session.getNickname(), session.getScore());
        }
        displayRanking();
    }

    private static void updateRanking(String nickname, int score) throws IOException {
        List<ScoreEntry> ranking = loadRanking();
        ranking.add(new ScoreEntry(nickname, score));
        ranking.sort((a, b) -> Integer.compare(b.score, a.score));
        if (ranking.size() > MAX_RANKING) {
            ranking = ranking.subList(0, MAX_RANKING);
        }
        saveRanking(ranking);
    }

    private static List<ScoreEntry> loadRanking() {
        List<ScoreEntry> ranking = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCORES_FILE))) {
            ranking = (List<ScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se encontró archivo de puntuaciones o no se pudo leer.");            
        }
        return ranking;
    }

    private static void saveRanking(List<ScoreEntry> ranking) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(ranking);
        }
    }

    private static void displayRanking() {
        List<ScoreEntry> ranking = loadRanking();
        System.out.println("\nRanking de puntuaciones:");
        for (ScoreEntry entry : ranking) {
            System.out.println(entry.nickname + " - " + entry.score + " puntos");
        }
    }

    private static class ScoreEntry implements Serializable {
        String nickname;
        int score;

        public ScoreEntry(String nickname, int score) {
            this.nickname = nickname;
            this.score = score;
        }
    }
}