package Model;

public class Score {

    private static int score = 0;

    public static int getScore() {
        return score;
    }

    public static void setScore(int newScore) {
        score = newScore;
    }

    public static void addPoints(int points) {
        score += points;
    }

    public static void resetScore() {
        score = 0;
    }
}
