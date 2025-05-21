package Model.Network;

import Model.Game;

public class NetworkManager {
    private Server server;
    private Client client;
    private boolean isServerMode;
    private static final int DEFAULT_PORT = 8080;
    private Game model;

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

        return connectToServer("localhost");
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
