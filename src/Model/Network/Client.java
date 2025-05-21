package Model.Network;

import java.io.*;
import java.net.*;

import Model.Game;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;
    private Game model;

    public Client(Game model) {
        this.model = model;
    }

    public void start(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            System.out.println("Connecté au serveur: " + host + ":" + port);

            new Thread(this::receiveMessages).start();

        } catch (IOException e) {
            System.out.println("Erreur de connexion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean sendValue(int value) {
        if (!connected || out == null) {
            System.out.println("Impossible d'envoyer la valeur: non connecté");
            return false;
        }

        try {
            String message = String.valueOf(value);
            out.println(message);
            System.out.println("Valeur envoyée: " + message);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi: " + e.getMessage());
            return false;
        }
    }

    public boolean sendScore(int score) {
        return sendValue(score);
    }

    private void receiveMessages() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                if (message.equals("START")) {
                    model.startGame();
                } else if (message.startsWith("SCORE:")) {
                    try {
                        int opponentScore = Integer.parseInt(message.substring(6));
                        System.out.println("Score adversaire: " + opponentScore);
                    } catch (NumberFormatException e) {
                        System.out.println("Format de score invalide");
                    }
                }
            }
        } catch (IOException e) {
            if (connected) {
                System.out.println("Erreur de lecture: " + e.getMessage());
            }
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        connected = false;
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
            System.out.println("Déconnecté du serveur");
        } catch (IOException e) {
            System.out.println("Erreur lors de la déconnexion: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected;
    }
}