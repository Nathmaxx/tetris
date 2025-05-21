package Model;

import java.io.*;

public class Score implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int score = 0;
    private static int bestScore = 0;
    private static final String BEST_SCORE_FILE = "best_score.dat";

    public static void addPoints(int points) {
        score += points;
        if (score > bestScore) {
            bestScore = score;
            saveBestScore();
        }
    }

    public static int getScore() {
        return score;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void resetScore() {
        score = 0;
    }

    public static void loadBestScore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BEST_SCORE_FILE))) {
            bestScore = ois.readInt();
        } catch (IOException e) {
            System.out.println("No previous best score found. Starting fresh.");
            bestScore = 0;
        }
    }

    private static void saveBestScore() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BEST_SCORE_FILE))) {
            oos.writeInt(bestScore);
        } catch (IOException e) {
            System.err.println("Failed to save best score: " + e.getMessage());
        }
    }
}
