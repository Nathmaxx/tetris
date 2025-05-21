package Model.Network;

import Model.Game;
import Model.Score;

public class NetworkManager {
    private Server server;
    private Client client;
    private boolean isServerMode;
    private Thread scoreUpdateThread;
    private static final int DEFAULT_PORT = 8080;
    private Game model;
    private boolean isSendingScores = false;

    public NetworkManager(Game model) {
        this.server = null;
        this.client = null;
        this.isServerMode = false;
        this.model = model;
    }

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

        if (connected) {
            startSendingScoresPublic();
        }

        return connected;
    }

    private void startSendingScores() {
        if (scoreUpdateThread != null && scoreUpdateThread.isAlive()) {
            stopSendingScores();
        }
        isSendingScores = true;
        scoreUpdateThread = new Thread(() -> {
            int lastScore = -1; // Pour éviter d'envoyer le même score plusieurs fois
            while (isSendingScores && client != null && client.isConnected() && !model.isGameOver()) {
                int currentScore = Score.getScore();
                // Envoyer le score seulement s'il a changé
                if (currentScore != lastScore) {
                    System.out.println("Envoi du score: " + currentScore + ", isServerMode: " + isServerMode);
                    sendScore(currentScore);
                    lastScore = currentScore;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("Thread d'envoi de score terminé. isSendingScores: " + isSendingScores +
                    ", client connecté: " + (client != null && client.isConnected()) +
                    ", gameOver: " + model.isGameOver());
        });

        scoreUpdateThread.start();
        System.out.println("Envoi automatique du score démarré");
    }

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
        System.out.println("Envoi automatique du score arrêté");
    }

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

    public boolean sendScore(int score) {
        if (client != null && client.isConnected()) {
            return client.sendScore(score);
        }
        return false;
    }

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

    // Méthode pour mettre à jour manuellement le score
    public void updateScore() {
        if (client != null && client.isConnected()) {
            sendScore(Score.getScore());
        }
    }

    // Méthode publique pour démarrer l'envoi automatique du score
    public void startSendingScoresPublic() {
        startSendingScores();
    }
}
