package Model.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe serveur, permet de partager les informations dans le cas d'une partie
 * en réseau au 2 clients
 */
public class Server {

    /** Socket du serveur qui écoute les connexions */
    private ServerSocket serverSocket;

    /** Liste des clients connectés au serveur */
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    /**
     * Démarre le serveur sur le port passé en paramètre et attend la connexion des
     * deux joueurs pour lancer la partie
     * 
     * @param port le port de démarrage du serveur
     */
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur lancé sur le port " + port);

            while (clients.size() < 2) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, this);
                clients.add(client);
                new Thread(client).start();
                System.out.println("Nouveau client connecté. Total clients : " + clients.size());

                if (clients.size() == 2) {
                    System.out.println("Deux joueurs connectés. Démarrage de la partie...");
                    broadcastMessage("START");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * envoie un message à tous les clients
     * 
     * @param message le message à envoyer
     */
    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * Envoie un message à tout le monde sauf au client passé en paramètre
     * Permet de communiquer dans notre cas à une seule personne
     * 
     * @param message       le message à envoyer
     * @param excludeClient le client à exclure pour l'envoi du message
     */
    public void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clients) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Reçoit le score d'un client et le diffuse à l'adversaire sous la forme
     * SCORE:<valeur>
     * 
     * @param value  la valeur du score
     * @param client le client initial
     */
    public void receiveValue(int value, ClientHandler client) {
        String formattedScore = "SCORE:" + value;
        broadcastMessage(formattedScore, client);
    }

    /**
     * Reçoit le message de fin de partie pour un des joueurs et le diffuse à
     * l'adversaire
     * 
     * @param client le client initial ayant perdu
     */
    public void receiveEndGame(ClientHandler client) {
        broadcastMessage("ENDGAME", client);
    }

    /**
     * Reçoit le message de partie gagnée pour un des joueurs et le diffuse à
     * l'adversaire
     * 
     * @param client le client initial ayant perdu
     */
    public void receiveWinGame(ClientHandler client) {
        broadcastMessage("WINGAME", client);
    }

    /**
     * Supprime un client de la liste
     * 
     * @param client le client à supprimer
     */
    public void deleteClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client déconnecté. Total clients : " + clients.size());
    }

}
