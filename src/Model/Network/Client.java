package Model.Network;

import java.io.*;
import java.net.*;

import Model.Game;

/**
 * Classe clientqui gère la communication réseau avec le serveur
 * 
 */
public class Client {

    /** Socket pour la connexion au serveur */
    private Socket socket;

    /** Flux de sortie pour envoyer des messages au serveur */
    private PrintWriter out;

    /** Flux d'entrée pour lire les messages du serveur */
    private BufferedReader in;

    /** État de la connexion avec le serveur */
    private boolean connected = false;

    /** Référence au modèle de jeu à mettre à jour */
    private Game model;

    /**
     * Constructeur qui initialise le client avec une référence au modèle de jeu.
     * 
     * @param model Le modèle de jeu qui sera mis à jour avec les informations
     *              reçues
     */
    public Client(Game model) {
        this.model = model;
    }

    /**
     * Établit une connexion avec le serveur et démarre un thread pour recevoir les
     * messages.
     * 
     * @param host L'adresse IP ou le nom d'hôte du serveur
     * @param port Le port sur lequel le serveur écoute
     */
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

    /**
     * Envoie le score actuel du joueur au serveur préfixé avec SCORE:
     * 
     * @param score La valeur du score à envoyer
     * @return true si le score a été envoyé avec succès, false sinon
     */
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

    /**
     * Envoie une information de jeu au serveur (comme ENDGAME ou WINGAME).
     * 
     * @param message Le message à envoyer au serveur
     * @return true si le message a été envoyé avec succès, false sinon
     */
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

    /** Reçoit et traite les messages en fonction de leur nature */
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

    /** Ferme la connexion avec le serveur */
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
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }
}