package Model;

import java.io.*;

/**
 * Classe contenant le score d'une partie, implémente de sérializable pour la
 * persistance du meilleur score
 */
public class Score implements Serializable {

    /** Identifiant du mécanisme de sérialisation */
    private static final long serialVersionUID = 1L;

    /** Le score de la partie en cours */
    private static int score = 0;

    /** Le meilleur score */
    private static int bestScore = 0;

    /** Le nom du fichier contenant les informations du meilleur score */
    private static final String BEST_SCORE_FILE = "best_score.dat";

    /**
     * Ajoute le nombre de points passé en entrée au score
     * 
     * @param points int, le nombre de points à ajouter
     */
    public static void addPoints(int points) {
        score += points;
        if (score > bestScore) {
            bestScore = score;
            saveBestScore();
        }
    }

    /** Charge le meilleur score sérialisé */
    public static void loadBestScore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BEST_SCORE_FILE))) {
            bestScore = ois.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            bestScore = 0;
        }
    }

    /** Sérialise le meilleur score */
    private static void saveBestScore() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BEST_SCORE_FILE))) {
            oos.writeInt(bestScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Getteurs et setteurs */
    public static int getScore() {
        return score;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void resetScore() {
        score = 0;
    }

    public static int getLevel() {
        return (score / 1000) + 1;
    }

}
