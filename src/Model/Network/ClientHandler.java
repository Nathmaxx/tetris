package Model.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe ClientHandler qui gère la communication individuelle avec chaque
 * client connecté au serveur.
 * 
 * Cette classe est responsable de :
 * - Recevoir et traiter les messages du client
 * - Envoyer des messages au client
 * - Maintenir la connexion socket avec le client
 * - Notifier le serveur des événements importants
 */
public class ClientHandler implements Runnable {

    /** Socket de connexion avec le client */
    private final Socket socket;

    /** Référence au serveur parent pour transmettre les messages */
    private final Server server;

    /** Flux d'entrée pour lire les messages du client */
    private BufferedReader in;

    /** Flux de sortie pour envoyer des messages au client */
    private PrintWriter out;

    /**
     * Constructeur qui initialise le gestionnaire de client avec sa socket et le
     * serveur parent
     * 
     * @param socket La socket de connexion avec le client
     * @param server Le serveur parent qui gère tous les clients
     */
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Méthode principale qui s'exécute dans un thread séparé.
     * Elle initialise les flux d'E/S et attend continuellement les messages du
     * client.
     * Les messages sont traités selon leur type (score, fin de partie, victoire).
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("SCORE:")) {
                    try {
                        int score = Integer.parseInt(line.substring(6));
                        server.receiveValue(score, this);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (line.equals("ENDGAME")) {
                    try {
                        server.receiveEndGame(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (line.equals("WINGAME")) {
                    try {
                        server.receiveWinGame(this);
                    } catch (Exception e) {
                        System.out.println("Erreur de réception de victoire de partie");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    /**
     * Envoie un message au client connecté à cette instance
     * 
     * @param message le message à envoyer
     */
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    /** Ferme la connexion avec le client et notifie le serveur */
    private void closeConnection() {
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.deleteClient(this);
    }
}