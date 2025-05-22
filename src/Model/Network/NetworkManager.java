package Model.Network;

import Model.Game;
import Model.Score;

/**
 * Classe NetworkManager qui gère toutes les communications réseau du jeu Tetris
 * multijoueur.
 * 
 * Cette classe fait le lien entre le modèle de jeu et les composants réseau
 */
public class NetworkManager {

    /** Instance du serveur */
    private Server server;

    /** Instance du client pour communiquer avec le serveur */
    private Client client;

    /** Indique si ce joueur héberge le serveur */
    private boolean isServerMode;

    /** Thread qui envoie périodiquement le score au serveur */
    private Thread scoreUpdateThread;

    /** Port par défaut utilisé pour les communications */
    private static final int DEFAULT_PORT = 8080;

    /** Instance de la partie en cours */
    private Game model;

    /** Contrôle l'état de la boucle d'envoi des scores */
    private boolean isSendingScores = false;

    /**
     * Constructeur du gestionnaire réseau
     * 
     * @param model Référence au modèle de jeu pour accéder aux données
     */
    public NetworkManager(Game model) {
        this.server = null;
        this.client = null;
        this.isServerMode = false;
        this.model = model;
    }

    /**
     * Démarre un serveur sur la machine d'un client et le connecte automatiquement
     * 
     * @return booléen indiquant si le serveur a démarré
     */
    public boolean startServer() {
        isServerMode = true;
        new Thread(() -> {
            server = new Server();
            server.start(DEFAULT_PORT);
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean connected = connectToServer("localhost");

        return connected;
    }

    /**
     * Démarre un thread qui envoie périodiquement le score actuel au serveur.
     * Vérifie également si la partie est gagné ou perdue et le communique au
     * serveur le cas échéant
     */
    public void startSendingScores() {
        if (scoreUpdateThread != null && scoreUpdateThread.isAlive()) {
            stopSendingScores();
        }
        isSendingScores = true;
        scoreUpdateThread = new Thread(() -> {
            int lastScore = -1;
            while (isSendingScores && client != null && client.isConnected() && !model.isGameOver()) {
                int currentScore = Score.getScore();
                if (currentScore != lastScore) {
                    sendScore(currentScore);
                    lastScore = currentScore;
                }

                if (model.isGameOver()) {
                    sendEndGame();
                }

                if (Score.getScore() > 1000) {
                    sendWinGame();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        scoreUpdateThread.start();
        System.out.println("Envoi automatique du score démarré");
    }

    /** Arrête d'envoyer les scores au serveur */
    private void stopSendingScores() {
        isSendingScores = false;
        if (scoreUpdateThread != null) {
            scoreUpdateThread.interrupt();
            try {
                scoreUpdateThread.join(1000);
            } catch (InterruptedException e) {
                System.err.println("Erreur lors de l'arrêt du thread d'envoi de score");
            }
            scoreUpdateThread = null;
        }
    }

    /**
     * Connecte un client à un serveur
     * 
     * @param host l'adresse ip du serveur
     * @return booléen, connexion réussie ou non
     */
    public boolean connectToServer(String host) {
        try {
            client = new Client(model);
            client.start(host, DEFAULT_PORT);
            return client.isConnected();
        } catch (Exception e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Envoie le score actuel au serveur.
     * 
     * @param score Le score à envoyer
     * @return true si l'envoi a réussi, false sinon
     */
    public boolean sendScore(int score) {
        if (client != null && client.isConnected()) {
            return client.sendScore(score);
        }
        return false;
    }

    /**
     * Envoie un message indiquant que le joueur a perdu la partie.
     * 
     * @return true si l'envoi a réussi, false sinon
     */
    public boolean sendEndGame() {
        if (client != null && client.isConnected()) {
            return client.sendGameInfo("ENDGAME");
        }
        return false;
    }

    /**
     * Envoie un message indiquant que le joueur a gagné la partie.
     * 
     * @return true si l'envoi a réussi, false sinon
     */
    public boolean sendWinGame() {
        if (client != null && client.isConnected()) {
            return client.sendGameInfo("WINGAME");
        }
        return false;
    }

    /**
     * Déconnecte le client du serveur et arrête l'envoi des scores.
     */
    public void disconnect() {
        stopSendingScores();
        if (client != null) {
            client.disconnect();
            client = null;
        }
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }

    public boolean isServerMode() {
        return isServerMode;
    }
}
