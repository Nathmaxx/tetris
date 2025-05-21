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

    public boolean sendScore(int score) {
        if (!connected || out == null) {
            System.out.println("Impossible d'envoyer le score: non connecté");
            return false;
        }

        try {
            String message = "SCORE:" + score;
            out.println(message);
            System.out.println("Score envoyé: " + score + " (format: '" + message + "')");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi du score: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendGameInfo(String message) {
        if (!connected || out == null) {
            System.out.println("Impossible d'envoyer : non connecté");
            return false;
        }

        try {
            out.println(message);
            System.out.println("Message envoyé au serveur");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi du message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                if (message.equals("START")) {
                    model.startGame();
                    model.setOpponentMessage("L'adversaire joue");
                } else if (message.startsWith("SCORE:")) {
                    try {
                        int opponentScore = Integer.parseInt(message.substring(6));
                        System.out.println("Score adversaire: " + opponentScore);
                        // Mettre à jour le score de l'adversaire dans le modèle
                        model.setOpponentScore(opponentScore);
                    } catch (NumberFormatException e) {
                        System.out.println("Format de score invalide");
                    }
                } else if (message.equals("ENDGAME")) {
                    model.setOpponentMessage("L'adversaire a perdu");
                } else if (message.equals("WINGAME")) {
                    model.setOpponentMessage("L'adversaire a gagné");
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